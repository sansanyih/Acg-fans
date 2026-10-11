package com.example.acgdata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 评论出参：字段名对齐前端 `Comment` 类型
 * （id / user / userAvatar / content / date）。
 */
@Data
public class CommentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 评论人用户名（前端字段名就是 user） */
    private String user;

    private String userAvatar;

    private String content;

    /** 格式 yyyy-MM-dd HH:mm，由 SQL 的 DATE_FORMAT 直接算好 */
    private String date;

    private Integer likeCount;
}
