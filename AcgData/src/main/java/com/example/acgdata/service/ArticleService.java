package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.common.PageResult;
import com.example.acgdata.dto.ArticleSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.entity.Article;
import com.example.acgdata.vo.ArticleDetailVO;
import com.example.acgdata.vo.ArticleItemVO;
import com.example.acgdata.vo.CommentVO;

import java.util.List;

/**
 * 文章服务。
 */
public interface ArticleService extends IService<Article> {

    /** 列表：GET /api/article/get */
    List<ArticleItemVO> listAll();

    /**
     * 分页 + 关键字条件查询：GET /api/article/page
     * 走 MyBatis-Plus 分页插件（单页上限 100），返回统一分页结构 PageResult。
     */
    PageResult<ArticleItemVO> pageArticles(long current, long size, String keyword);

    /** 详情（含评论）：GET /api/article/get/{id} */
    ArticleDetailVO getDetail(Long id);

    /**
     * 关键词搜索：GET /api/article/search?keyword=
     * 供 SearchController 复用，返回类型不可更改。
     */
    List<ArticleItemVO> search(String keyword);

    /** 新增：POST /api/article/insert */
    ArticleDetailVO saveArticle(ArticleSaveDTO dto);

    /** 修改：PUT /api/article/update/{id} */
    ArticleDetailVO updateArticle(Long id, ArticleSaveDTO dto);

    /** 逻辑删除：DELETE /api/article/delete/{id} */
    void removeArticle(Long id);

    /** 发表评论：POST /api/article/addComment/{id} */
    CommentVO addComment(Long id, CommentSaveDTO dto);

    /** 点赞：POST /api/article/like/{id}，返回更新后的详情（likeCount 已 +1） */
    ArticleDetailVO like(Long id);

    /** 浏览：POST /api/article/view/{id}，返回更新后的详情（readCount 已 +1） */
    ArticleDetailVO view(Long id);
}
