package com.example.acgdata.vo;

import com.example.acgdata.entity.WeeklyReport;
import lombok.Data;

import java.io.Serializable;

/**
 * 周报列表出参：字段与旧接口 GET /api/weekly/get 的每一项逐个对齐
 * （不包含 content / lastEdit / comments）。
 */
@Data
public class WeeklyItemVO implements Serializable {

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
    public static WeeklyItemVO from(WeeklyReport report) {
        WeeklyItemVO vo = new WeeklyItemVO();
        vo.setId(report.getId());
        vo.setCover(report.getCover());
        vo.setTitle(report.getTitle());
        vo.setSummary(report.getSummary());
        vo.setAuthor(report.getAuthor());
        vo.setAuthorAvatar(report.getAuthorAvatar());
        vo.setPublishDate(report.getPublishDate() == null ? null : report.getPublishDate().toString());
        vo.setReadCount(report.getReadCount());
        vo.setLikeCount(report.getLikeCount());
        return vo;
    }
}
