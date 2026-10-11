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
 * 动态表。
 *
 * <p>{@code publishTime} 只用于排序，{@code timeLabel} 是直接返回给前端的展示文案
 * （如「2026年04月10日」「2天前」「刚刚」），保证与旧接口逐字一致。
 */
@Data
@TableName("feed")
public class Feed implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userAvatar;

    private String username;

    /** 发布时间（排序用） */
    private LocalDateTime publishTime;

    /** 展示时间文案（前端字段名 time） */
    private String timeLabel;

    private String content;

    private String link;

    /** 全局约定列：每个业务表都要有 like_count */
    private Integer likeCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
