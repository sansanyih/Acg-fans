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
 * 角色表。
 *
 * <p>注意：{@code character} 是 MySQL 保留字，直接写 {@code FROM character} 会报语法错误，
 * 因此这里用反引号包住表名（MyBatis-Plus 会原样拼进 SQL）。
 *
 * <p>所属番剧只用 {@code anime_id} 逻辑关联，不加外键约束；
 * 点赞 / 踩用冗余计数列 {@code like_count} / {@code dislike_count}，
 * 返回给前端时由 CharacterItemVO 翻译成 {@code likes} / {@code dislikes}。
 */
@Data
@TableName("`character`")
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色名（中文） */
    private String name;

    private String japaneseName;

    private String image;

    /** 所属番剧 id（逻辑关联，无外键） */
    private Long animeId;

    /** 所属番剧原名 */
    private String animeTitle;

    /** 所属番剧中文名 */
    private String animeChineseTitle;

    private String voiceActor;

    /** 历史字段：与 voiceActor 同值，前端两个字段都在用 */
    private String cv;

    private String gender;

    private Integer age;

    /** 身高（含单位，如 195cm） */
    private String height;

    /** 体重（含单位，如 82kg） */
    private String weight;

    /** 生日，格式 MM-dd（前端按该格式比对「今日生日」） */
    private String birthday;

    private String description;

    /** 点赞数（前端字段名 likes） */
    private Integer likeCount;

    /** 踩数（前端字段名 dislikes） */
    private Integer dislikeCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
