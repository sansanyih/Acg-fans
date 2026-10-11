package com.example.acgdata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 公告评论出参：字段与旧接口 POST /api/announcement/addComment/{id} 及详情里的
 * comments 每一项对齐（id / user / userAvatar / content / date / replyCount）。
 */
@Data
public class AnnouncementCommentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 评论人用户名（前端字段名就是 user） */
    private String user;

    private String userAvatar;

    private String content;

    /** 格式 yyyy-MM-dd HH:mm，由共享 CommentService 的 SQL 算好 */
    private String date;

    /** 楼中楼回复数：当前未实现回复功能，固定 0（与旧接口一致） */
    private Integer replyCount;

    public static AnnouncementCommentVO from(CommentVO comment) {
        AnnouncementCommentVO vo = new AnnouncementCommentVO();
        vo.setId(comment.getId());
        vo.setUser(comment.getUser());
        vo.setUserAvatar(comment.getUserAvatar());
        vo.setContent(comment.getContent());
        vo.setDate(comment.getDate());
        vo.setReplyCount(0);
        return vo;
    }
}
