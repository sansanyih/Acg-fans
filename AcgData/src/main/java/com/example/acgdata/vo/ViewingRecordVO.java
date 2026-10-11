package com.example.acgdata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 观影记录出参：字段对齐前端 `ViewingRecord`
 * （id / user / userAvatar / rating / totalRating / musicRating / artRating /
 * scriptRating / performanceRating / programRating / voiceRating / comment / date）。
 *
 * <p>直接由 `anime_rating` 的查询结果映射而来。
 */
@Data
public class ViewingRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 评分人昵称 */
    private String user;

    private String userAvatar;

    /** 总评分 */
    private Double rating;

    /** 总分（满分） */
    private Integer totalRating;

    private Integer musicRating;

    private Integer artRating;

    private Integer scriptRating;

    private Integer performanceRating;

    private Integer programRating;

    private Integer voiceRating;

    private String comment;

    /** 评分时间，固定输出 yyyy-MM-dd HH:mm:ss */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
