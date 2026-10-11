package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.AnnouncementSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.entity.Announcement;
import com.example.acgdata.vo.AnnouncementCommentVO;
import com.example.acgdata.vo.AnnouncementListVO;
import com.example.acgdata.vo.AnnouncementVO;

import java.util.List;

/**
 * 公告服务。
 * 评论走共享 CommentService（targetType = announcement），
 * 点赞走共享 LikeService（targetType = announcement，内部维护 announcement.like_count）。
 */
public interface AnnouncementService extends IService<Announcement> {

    /** 公告列表（卡片字段，按发布日期倒序） */
    List<AnnouncementListVO> listItem();

    /** 公告详情（含正文、lastEdit、评论列表） */
    AnnouncementVO getVO(Long id);

    /** 新增公告，返回保存后的完整对象 */
    AnnouncementVO saveAnnouncement(AnnouncementSaveDTO dto);

    /** 修改公告，返回更新后的完整对象（lastEdit 会刷新成当前时间） */
    AnnouncementVO updateAnnouncement(Long id, AnnouncementSaveDTO dto);

    /** 逻辑删除公告，查不到抛业务异常 */
    void removeAnnouncement(Long id);

    /** 发表评论，评论人取自 JWT */
    AnnouncementCommentVO addComment(Long id, CommentSaveDTO dto);

    /** 点赞，返回最新点赞数；重复点赞抛业务异常 */
    long like(Long id);
}
