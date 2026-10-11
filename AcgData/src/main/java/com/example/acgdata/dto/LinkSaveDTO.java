package com.example.acgdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 友情链接新增 / 修改入参。
 */
@Data
public class LinkSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "网站名称不能为空")
    @Size(max = 50, message = "网站名称不能超过 50 字")
    private String name;

    /** 图标地址 */
    private String iconUrl;

    /** 跳转地址 */
    @NotBlank(message = "跳转链接不能为空")
    private String targetUrl;

    private Integer sort;
}
