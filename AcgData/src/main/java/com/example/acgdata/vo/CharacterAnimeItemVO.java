package com.example.acgdata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 「角色所属番剧」下的角色列表项：旧接口 `GET /api/character/getByAnime/{animeId}`
 * 返回 id / name / image / voiceActor / likes（**没有** animeChineseTitle），这里保持一致。
 *
 * <p>与 {@link CharacterItemVO} 的差别只在于「没有 animeChineseTitle」，
 * 分开两个类是为了逐字段对齐旧接口的返回结构。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterAnimeItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String image;

    private String voiceActor;

    /** 点赞数（旧接口字段名 likes） */
    private Integer likes;
}
