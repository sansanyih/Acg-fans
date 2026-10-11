package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.AnnouncementSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.entity.Announcement;
import com.example.acgdata.mapper.AnnouncementMapper;
import com.example.acgdata.service.AnnouncementService;
import com.example.acgdata.service.CommentService;
import com.example.acgdata.service.LikeService;
import com.example.acgdata.vo.AnnouncementCommentVO;
import com.example.acgdata.vo.AnnouncementListVO;
import com.example.acgdata.vo.AnnouncementVO;
import com.example.acgdata.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 公告服务实现。
 * 点赞计数完全交给 LikeService（它内部维护 announcement.like_count），这里不再自己加减。
 */
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    /** 评论 / 点赞的目标类型，取值见 REFACTOR_GUIDE §7.2 */
    private static final String TARGET_TYPE = "announcement";

    private static final String DEFAULT_COVER = "https://example.com/default-cover.jpg";
    private static final String DEFAULT_AUTHOR = "系统管理员";
    private static final String DEFAULT_AUTHOR_AVATAR = "https://example.com/admin-avatar.jpg";

    private final CommentService commentService;
    private final LikeService likeService;

    @Override
    public List<AnnouncementListVO> listItem() {
        return baseMapper.selectListVO();
    }

    @Override
    public AnnouncementVO getVO(Long id) {
        getExisting(id);
        AnnouncementVO vo = baseMapper.selectVOById(id);
        vo.setComments(listComments(id));
        return vo;
    }

    @Override
    public AnnouncementVO saveAnnouncement(AnnouncementSaveDTO dto) {
        Announcement announcement = new Announcement();
        apply(announcement, dto);
        // 与旧接口一致：新公告的阅读量 / 点赞数归零，发布日期取当天
        announcement.setReadCount(0);
        announcement.setLikeCount(0);
        announcement.setPublishDate(LocalDate.now());
        save(announcement);
        return getVO(announcement.getId());
    }

    @Override
    public AnnouncementVO updateAnnouncement(Long id, AnnouncementSaveDTO dto) {
        Announcement announcement = getExisting(id);
        apply(announcement, dto);
        updateById(announcement);
        return getVO(id);
    }

    @Override
    public void removeAnnouncement(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic）
        removeById(id);
    }

    @Override
    public AnnouncementCommentVO addComment(Long id, CommentSaveDTO dto) {
        getExisting(id);
        return AnnouncementCommentVO.from(commentService.add(TARGET_TYPE, id, dto));
    }

    @Override
    public long like(Long id) {
        getExisting(id);
        return likeService.like(TARGET_TYPE, id);
    }

    /** 查不到统一抛业务异常（不要返回 null，也不要自己 try/catch） */
    private Announcement getExisting(Long id) {
        Announcement announcement = getById(id);
        if (announcement == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return announcement;
    }

    /** 评论列表：共享 CommentService 直接返回 CommentVO，再翻译成旧接口的字段结构 */
    private List<AnnouncementCommentVO> listComments(Long id) {
        return commentService.listByTarget(TARGET_TYPE, id).stream()
                .map(AnnouncementCommentVO::from)
                .toList();
    }

    /**
     * DTO -> 实体。
     * 更新场景下只覆盖 title / content / summary / cover / author / authorAvatar，
     * id、readCount、likeCount、publishDate 一律保留（与旧接口行为一致）。
     */
    private void apply(Announcement announcement, AnnouncementSaveDTO dto) {
        announcement.setTitle(dto.getTitle());
        announcement.setContent(dto.getContent());
        announcement.setSummary(dto.getSummary());
        announcement.setCover(isBlank(dto.getCover()) ? DEFAULT_COVER : dto.getCover());
        announcement.setAuthor(isBlank(dto.getAuthor()) ? DEFAULT_AUTHOR : dto.getAuthor());
        announcement.setAuthorAvatar(isBlank(dto.getAuthorAvatar()) ? DEFAULT_AUTHOR_AVATAR : dto.getAuthorAvatar());
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
