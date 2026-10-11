package com.example.acgdata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 点赞记录表：唯一索引 (target_type, target_id, user_id) 防止重复点赞。
 * 说明：这张表刻意**不做逻辑删除**（取消点赞直接物理删除），
 * 否则「逻辑删除 + 唯一索引」会导致同一用户的历史记录互相冲突。
 */
@Data
@TableName("like_record")
public class LikeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** anime / article / video / announcement / weekly */
    private String targetType;

    private Long targetId;

    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
