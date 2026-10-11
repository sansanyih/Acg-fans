package com.example.acgdata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 番剧观影记录 / 评分表（db/schema/10_anime.sql 的 `anime_rating`）。
 *
 * <p>对应旧接口详情里的 `viewingRecords` 数组与 `/get/random-ratings` 列表。
 * 评分维度沿用前端 `ViewingRecord` / `RatingItem` 的字段名。
 */
@Data
@TableName("anime_rating")
public class AnimeRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long animeId;

    /** 评分人昵称（历史演示数据没有关联 user 表，故直接存名字） */
    private String user;

    private String userAvatar;

    /** 总评分 */
    private Double rating;

    /** 总分（满分，前端雷达图按 10 分制展示） */
    private Integer totalRating;

    /** 音乐 */
    private Integer musicRating;

    /** 美术 */
    private Integer artRating;

    /** 剧本 */
    private Integer scriptRating;

    /** 演出 */
    private Integer performanceRating;

    /** 节目 */
    private Integer programRating;

    /** 声优 */
    private Integer voiceRating;

    /** 短评 */
    private String comment;

    /** 评分时间 */
    private LocalDateTime rateDate;

    /** 列表展示用的稳定序号（同一番剧下唯一，保证种子 SQL 幂等） */
    private Integer sortOrder;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
