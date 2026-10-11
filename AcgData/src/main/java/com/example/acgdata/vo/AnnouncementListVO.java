package com.example.acgdata.vo;

import com.example.acgdata.entity.Announcement;
import lombok.Data;

import java.io.Serializable;

/**
 * 公告列表出参：字段与旧接口 GET /api/announcement/get 的每一项逐个对齐
 * （不包含 content / lastEdit / comments）。
 */
@Data
public class AnnouncementListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String cover;

    private String title;

    private String summary;

    private String author;

    private String authorAvatar;

    /** 格式 yyyy-MM-dd（SQL 里 DATE_FORMAT 直接算好） */
    private String publishDate;

    private Integer readCount;

    private Integer likeCount;

    /** 实体 -> 列表出参（publishDate 直接用 LocalDate.toString()，格式就是 yyyy-MM-dd） */
    public static AnnouncementListVO from(Announcement announcement) {
        AnnouncementListVO vo = new AnnouncementListVO();
        vo.setId(announcement.getId());
        vo.setCover(announcement.getCover());
        vo.setTitle(announcement.getTitle());
        vo.setSummary(announcement.getSummary());
        vo.setAuthor(announcement.getAuthor());
        vo.setAuthorAvatar(announcement.getAuthorAvatar());
        vo.setPublishDate(announcement.getPublishDate() == null ? null : announcement.getPublishDate().toString());
        vo.setReadCount(announcement.getReadCount());
        vo.setLikeCount(announcement.getLikeCount());
        return vo;
    }
}
