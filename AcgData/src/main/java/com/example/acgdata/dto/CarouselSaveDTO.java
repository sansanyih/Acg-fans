package com.example.acgdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 轮播图新增 / 修改入参（前端只需要 url + title，sort 可选、用于控制展示顺序）。
 */
@Data
public class CarouselSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "图片地址不能为空")
    @Size(max = 500, message = "图片地址不能超过 500 字")
    private String url;

    @NotBlank(message = "轮播图标题不能为空")
    @Size(max = 200, message = "轮播图标题不能超过 200 字")
    private String title;

    /** 排序值，越小越靠前；不传默认 0 */
    private Integer sort;
}
