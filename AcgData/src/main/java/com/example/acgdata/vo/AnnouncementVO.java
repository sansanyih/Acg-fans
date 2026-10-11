package com.example.acgdata.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 公告详情出参：字段与旧接口 GET /api/announcement/get/{id} 对齐
 * （列表字段 + content + lastEdit + comments）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnnouncementVO extends AnnouncementListVO {

    private static final long serialVersionUID = 1L;

    private String content;

    /** 格式 yyyy年M月d日 HH:mm:ss，取 update_time（SQL 里 DATE_FORMAT 直接算好） */
    private String lastEdit;

    /** 该公告下的评论（共享 CommentService 查出） */
    private List<AnnouncementCommentVO> comments;
}
