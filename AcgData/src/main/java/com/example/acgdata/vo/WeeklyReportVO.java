package com.example.acgdata.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 周报详情出参：字段与旧接口 GET /api/weekly/get/{id} 对齐，
 * 对应前端 `WeeklyDetail extends WeeklyItem`（列表字段 + content + lastEdit + comments）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WeeklyReportVO extends WeeklyItemVO {

    private static final long serialVersionUID = 1L;

    private String content;

    /** 格式 yyyy年M月d日 HH:mm:ss，取 update_time（SQL 里 DATE_FORMAT 直接算好） */
    private String lastEdit;

    /** 该周报下的评论（共享 CommentService 查出） */
    private List<WeeklyCommentVO> comments;
}
