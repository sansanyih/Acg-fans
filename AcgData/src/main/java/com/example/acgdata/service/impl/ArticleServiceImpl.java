package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.PageResult;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.ArticleSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.entity.Article;
import com.example.acgdata.mapper.ArticleMapper;
import com.example.acgdata.service.ArticleService;
import com.example.acgdata.service.CommentService;
import com.example.acgdata.service.EditStatisticService;
import com.example.acgdata.service.LikeService;
import com.example.acgdata.vo.ArticleDetailVO;
import com.example.acgdata.vo.ArticleItemVO;
import com.example.acgdata.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章服务实现。
 *
 * <p>依赖的评论 / 点赞 / 编辑统计都是共享服务，这里只做编排，不再自己维护计数：
 * <ul>
 *     <li>评论：{@link CommentService#listByTarget(String, Long)}，targetType = "article"；</li>
 *     <li>点赞：{@link LikeService#like(String, Long)}，内部会维护 article.like_count；</li>
 *     <li>编辑统计：{@link EditStatisticService#increment(String)}，type = "article"。</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    /** 多态评论 / 点赞的目标类型 */
    private static final String TARGET_TYPE = "article";

    private static final String DEFAULT_COVER = "https://example.com/default-cover.jpg";

    private static final String DEFAULT_AVATAR = "https://example.com/default-avatar.jpg";

    private final CommentService commentService;
    private final LikeService likeService;
    private final EditStatisticService editStatisticService;

    @Override
    public List<ArticleItemVO> listAll() {
        return list(Wrappers.<Article>lambdaQuery()
                .orderByDesc(Article::getPublishDate)
                .orderByDesc(Article::getId))
                .stream()
                .map(ArticleItemVO::from)
                .toList();
    }

    @Override
    public PageResult<ArticleItemVO> pageArticles(long current, long size, String keyword) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .like(StringUtils.hasText(keyword), Article::getTitle, keyword)
                .orderByDesc(Article::getPublishDate)
                .orderByDesc(Article::getId);
        // 逻辑删除条件由 @TableLogic 自动拼上，这里不用手写 deleted = 0
        return PageResult.of(baseMapper.selectPage(page, wrapper), ArticleItemVO::from);
    }

    @Override
    public ArticleDetailVO getDetail(Long id) {
        return toDetail(getExisting(id));
    }

    @Override
    public List<ArticleItemVO> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return List.of();
        }
        String kw = keyword.trim();
        return list(Wrappers.<Article>lambdaQuery()
                .and(w -> w.like(Article::getTitle, kw)
                        .or().like(Article::getContent, kw)
                        .or().like(Article::getAuthor, kw)
                        .or().like(Article::getSummary, kw))
                .orderByDesc(Article::getPublishDate)
                .orderByDesc(Article::getId))
                .stream()
                .map(ArticleItemVO::from)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDetailVO saveArticle(ArticleSaveDTO dto) {
        Article article = new Article();
        apply(article, dto);
        article.setReadCount(0);
        article.setLikeCount(0);
        if (article.getPublishDate() == null) {
            article.setPublishDate(LocalDateTime.now());
        }
        article.setCover(StringUtils.hasText(dto.getCover()) ? dto.getCover() : DEFAULT_COVER);
        article.setAuthorAvatar(StringUtils.hasText(dto.getAuthorAvatar()) ? dto.getAuthorAvatar() : DEFAULT_AVATAR);
        save(article);

        editStatisticService.increment(TARGET_TYPE);
        // 新增时还没有评论，直接返回空评论列表，省一次查询
        return ArticleDetailVO.from(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDetailVO updateArticle(Long id, ArticleSaveDTO dto) {
        Article article = getExisting(id);
        apply(article, dto);
        updateById(article);

        editStatisticService.increment(TARGET_TYPE);
        return toDetail(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeArticle(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic），评论与点赞记录保留在各自表里
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentVO addComment(Long id, CommentSaveDTO dto) {
        getExisting(id);
        return commentService.add(TARGET_TYPE, id, dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDetailVO like(Long id) {
        Article article = getExisting(id);
        // 重复点赞会抛 BusinessException(REPEAT_OPERATION)
        long likeCount = likeService.like(TARGET_TYPE, id);
        article.setLikeCount((int) likeCount);
        return toDetail(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDetailVO view(Long id) {
        getExisting(id);
        // 原子自增，避免并发浏览时读改写丢计数
        update(Wrappers.<Article>lambdaUpdate()
                .setSql("read_count = read_count + 1")
                .eq(Article::getId, id));
        return getDetail(id);
    }

    /** 查不到直接抛业务异常，由全局异常处理器收敛 */
    private Article getExisting(Long id) {
        Article article = getById(id);
        if (article == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return article;
    }

    private ArticleDetailVO toDetail(Article article) {
        return ArticleDetailVO.from(article, commentService.listByTarget(TARGET_TYPE, article.getId()));
    }

    /** DTO -> 实体（新增与修改共用；作者信息按旧接口语义直接采用入参） */
    private void apply(Article article, ArticleSaveDTO dto) {
        article.setTitle(dto.getTitle());
        article.setSummary(dto.getSummary());
        article.setContent(dto.getContent());
        article.setCover(dto.getCover());
        article.setAuthor(dto.getAuthor());
        article.setAuthorAvatar(dto.getAuthorAvatar());
        if (dto.getPublishDate() != null) {
            article.setPublishDate(dto.getPublishDate());
        }
    }
}
