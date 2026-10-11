package com.example.acgdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 动态新增 / 修改入参。
 *
 * <p>说明：{@code time} 不接受前端传值 —— 旧接口 insert 固定返回「刚刚」，
 * update 明确忽略 time/id 字段，这里保持同样的语义。
 */
@Data
public class FeedSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userAvatar;

    @NotBlank(message = "发布者名称不能为空")
    @Size(max = 100, message = "发布者名称不能超过 100 字")
    private String username;

    @NotBlank(message = "动态内容不能为空")
    private String content;

    /** 跳转链接，insert 时缺省为 https://example.com/feed/new（与旧接口一致） */
    private String link;
}
