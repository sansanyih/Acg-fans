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
 * 公告表。
 * 说明：不单独存 lastEdit —— update_time 就是出参里的 lastEdit（SQL 里 DATE_FORMAT 成
 * {@code yyyy年M月d日 HH:mm:ss}），保持与旧接口逐字一致。
 */
@Data
@TableName("announcement")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String cover;

    private String title;

    /** 正文（Markdown） */
    private String content;

    private String summary;

    private String author;

    private String authorAvatar;

    /** 前端字段 publishDate，格式 yyyy-MM-dd */
    private LocalDate publishDate;

    private Integer readCount;

    /** 点赞数由共享 LikeService 维护，业务代码不要自己加减 */
    private Integer likeCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
