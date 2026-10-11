package com.example.acgdata.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 番剧新增 / 修改入参：字段与前端 `AnimeDetail`（表单）一一对应。
 *
 * <p>旧实现是 `@RequestBody Map<String,Object>`，这里换成 DTO + Jakarta Validation。
 * 更新语义与旧接口保持一致（旧代码是 `putAll`）：**只覆盖请求里出现的字段**，
 * 为 null 的字段保持数据库原值，因此除 title 外都不加 @NotNull。
 */
@Data
public class AnimeSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "番剧标题不能为空")
    @Size(max = 200, message = "番剧标题不能超过 200 字")
    private String title;

    @Size(max = 200, message = "中文标题不能超过 200 字")
    private String chineseTitle;

    @Size(max = 500, message = "封面地址不能超过 500 字")
    private String image;

    @Size(max = 500, message = "简介不能超过 500 字")
    private String description;

    @Size(max = 2000, message = "详细介绍不能超过 2000 字")
    private String introduction;

    @Size(max = 200, message = "制作公司不能超过 200 字")
    private String studio;

    /** 标签名列表：Service 会调用 TagService.bindAnimeTags 重建关联 */
    private List<String> tags;

    @Min(value = 0, message = "集数不能为负数")
    private Integer episodes;

    /** 开播日期，格式 yyyy-MM-dd（前端日期选择器直接传字符串） */
    @Size(max = 10, message = "开播日期格式应为 yyyy-MM-dd")
    private String startDate;

    @Size(max = 20, message = "播出星期不能超过 20 字")
    private String broadcastDay;

    @Size(max = 200, message = "原作不能超过 200 字")
    private String originalWork;

    @Size(max = 200, message = "监督不能超过 200 字")
    private String director;

    @Size(max = 200, message = "脚本不能超过 200 字")
    private String script;

    @Size(max = 200, message = "分镜不能超过 200 字")
    private String storyboard;

    @Size(max = 200, message = "演出不能超过 200 字")
    private String performance;

    @Size(max = 200, message = "音乐不能超过 200 字")
    private String music;

    @Size(max = 200, message = "人物设计不能超过 200 字")
    private String characterDesign;

    @Size(max = 200, message = "系列构成不能超过 200 字")
    private String seriesComposition;

    @Size(max = 200, message = "美术监督不能超过 200 字")
    private String artDirector;

    @Size(max = 200, message = "色彩设计不能超过 200 字")
    private String colorDesign;

    @Size(max = 200, message = "总作画监督不能超过 200 字")
    private String chiefAnimationDirector;

    @Size(max = 500, message = "官网地址不能超过 500 字")
    private String officialWebsite;
}
