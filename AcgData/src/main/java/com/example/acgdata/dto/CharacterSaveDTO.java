package com.example.acgdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色新增 / 修改入参。
 *
 * <p>说明：旧接口用 {@code Map<String,Object>} 接收入参且是「增删改都是同一份」的半宽松语义，
 * 这里改成 DTO + 校验；除 {@code name} / {@code animeId} 必填外，其余字段允许缺省，
 * update 时 null 字段表示「不修改该字段」，与旧接口 putAll 的行为对齐。
 */
@Data
public class CharacterSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "角色名不能为空")
    @Size(max = 100, message = "角色名不能超过 100 字")
    private String name;

    @Size(max = 100, message = "日文名不能超过 100 字")
    private String japaneseName;

    private String image;

    /** 所属番剧 id（逻辑关联，无外键） */
    private Long animeId;

    private String animeTitle;

    private String animeChineseTitle;

    private String voiceActor;

    private String cv;

    private String gender;

    private Integer age;

    private String height;

    private String weight;

    /** 生日，格式 MM-dd */
    private String birthday;

    private String description;
}
