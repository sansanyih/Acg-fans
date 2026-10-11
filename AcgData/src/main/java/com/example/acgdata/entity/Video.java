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
 * 视频表。
 *
 * <p>作者信息、关联标签、关联番剧都以冗余字符串存在本表，原因：
 * <ul>
 *     <li>作者信息是旧接口 JSON 形状的一部分，冗余成字符串最省事；</li>
 *     <li>anime 模块尚未落库，不能在编译期依赖 Anime 实体，
 *         所以关联番剧只存 id，出参时包装成占位对象（详见 VideoDetailVO）。</li>
 * </ul>
 */
@Data
@TableName("video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    /** 封面图（前端字段 thumbnail） */
    private String thumbnail;

    private String author;

    private String authorAvatar;

    /** 作者身份（前端字段 authorRole） */
    private String authorRole;

    /** 作者等级（前端字段 authorLevel） */
    private Integer authorLevel;

    /** 作者简介（前端字段 authorBio） */
    private String authorBio;

    /** 发布时间，出参格式 yyyy-MM-dd HH:mm:ss */
    private LocalDateTime publishDate;

    private String description;

    /** 外部播放地址（前端字段 externalLink） */
    private String externalLink;

    /** 关联标签，英文逗号分隔；出参转 string[] */
    private String relatedTags;

    /** 关联番剧 id，英文逗号分隔；出参转对象数组 */
    private String relatedAnime;

    private String duration;

    private Integer viewCount;

    /** 点赞数（前端字段 likeCount），由 LikeService 维护 */
    private Integer likeCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
