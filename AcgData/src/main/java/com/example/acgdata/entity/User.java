package com.example.acgdata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户表。
 * 注意：password 用 WRITE_ONLY，序列化时永不返回给前端。
 */
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    /** BCrypt 加密后的密码，只允许写入、永不序列化 */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String avatar;

    private LocalDate registerDate;

    private LocalDateTime lastLogin;

    private Integer articleCount;

    private Integer commentCount;

    private String bio;

    private Integer level;

    /**
     * 是否管理员。
     * 用包装类型 Boolean 而不是 boolean：Lombok 对 boolean isAdmin 生成 isAdmin()，
     * Jackson 会序列化成 "admin"，字段名就与前端不一致了。
     */
    private Boolean isAdmin;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 逻辑删除标记，不参与查询返回 */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
