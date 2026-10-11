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
 * 文章表。
 * 作者信息（author / authorAvatar）冗余成字符串列，保证旧接口的 JSON 形状不变。
 */
@Data
@TableName("article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String summary;

    /** 正文，长文本（TEXT 列） */
    private String content;

    private String cover;

    /** 作者名（前端字段 author） */
    private String author;

    /** 作者头像（前端字段 authorAvatar） */
    private String authorAvatar;

    /** 发布日期，出参格式 yyyy-MM-dd */
    private LocalDateTime publishDate;

    /** 阅读数（前端字段 readCount） */
    private Integer readCount;

    /** 点赞数（前端字段 likeCount），由 LikeService 维护 */
    private Integer likeCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间，出参格式化为前端的 lastEdit */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
