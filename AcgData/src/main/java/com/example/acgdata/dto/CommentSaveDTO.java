package com.example.acgdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 发表评论入参。
 * 注意：评论人不再由前端传，统一从 JWT 中取当前登录用户。
 */
@Data
public class CommentSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 1000, message = "评论内容不能超过 1000 字")
    private String content;

    /** 父评论 id，可为空（楼中楼） */
    private Long parentId;
}
