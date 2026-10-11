package com.example.acgdata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 番剧表（db/schema/10_anime.sql 的 `anime`）。
 *
 * <p>说明：
 * <ul>
 *     <li>标签是多对多关系，存在 `anime_tag` 关联表，由 TagService 维护，本实体不映射；</li>
 *     <li>`type` 是旧接口返回的固定标记（恒为 anime），不是数据库列，只在 VO 里输出；</li>
 *     <li>旧接口的 `lastEdit` 由 `update_time` 格式化成 `yyyy-MM-dd HH:mm:ss` 输出，
 *         因此每次修改都会被 {@code @TableField(fill = INSERT_UPDATE)} 刷新。</li>
 * </ul>
 */
@Data
@TableName("anime")
public class Anime implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 日文原名 */
    private String title;

    /** 中文译名 */
    private String chineseTitle;

    /** 封面图 */
    private String image;

    /** 短描述（列表页展示） */
    private String description;

    /** 详细介绍（详情页展示） */
    private String introduction;

    /** 制作公司 */
    private String studio;

    /** 集数 */
    private Integer episodes;

    /** 开播日期 */
    private LocalDate startDate;

    /** 播出星期，例如「星期四」「不定期」 */
    private String broadcastDay;

    /** 原作 */
    private String originalWork;

    /** 监督 */
    private String director;

    /** 脚本 */
    private String script;

    /** 分镜 */
    private String storyboard;

    /** 演出 */
    private String performance;

    /** 音乐 */
    private String music;

    /** 人物设计 */
    private String characterDesign;

    /** 系列构成 */
    private String seriesComposition;

    /** 美术监督 */
    private String artDirector;

    /** 色彩设计 */
    private String colorDesign;

    /** 总作画监督 */
    private String chiefAnimationDirector;

    /** 官方网站 */
    private String officialWebsite;

    /** 点赞数（由 LikeService 原子维护，业务模块不要自己加减） */
    private Integer likeCount;

    /** 排序值，越小越靠前（作为列表的稳定排序兜底） */
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
