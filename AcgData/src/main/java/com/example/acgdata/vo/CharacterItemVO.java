package com.example.acgdata.vo;

import com.example.acgdata.entity.Character;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色完整出参：字段名逐个对齐前端 `Character` 类型（AcgWeb/src/api/modules/character/interface.ts）。
 *
 * <p>注意：旧接口把实体内部的 {@code likeCount} / {@code dislikeCount} 翻译成
 * 前端认的 {@code likes} / {@code dislikes}，这里沿用同样的命名。
 */
@Data
public class CharacterItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String japaneseName;

    private String image;

    private Long animeId;

    private String animeTitle;

    private String animeChineseTitle;

    private String voiceActor;

    private String gender;

    private Integer age;

    private String height;

    private String weight;

    private String birthday;

    private String description;

    private String cv;

    /** 点赞数（旧接口字段名 likes） */
    private Integer likes;

    /** 踩数（旧接口字段名 dislikes） */
    private Integer dislikes;

    public static CharacterItemVO from(Character entity) {
        CharacterItemVO vo = new CharacterItemVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setJapaneseName(entity.getJapaneseName());
        vo.setImage(entity.getImage());
        vo.setAnimeId(entity.getAnimeId());
        vo.setAnimeTitle(entity.getAnimeTitle());
        vo.setAnimeChineseTitle(entity.getAnimeChineseTitle());
        vo.setVoiceActor(entity.getVoiceActor());
        vo.setGender(entity.getGender());
        vo.setAge(entity.getAge());
        vo.setHeight(entity.getHeight());
        vo.setWeight(entity.getWeight());
        vo.setBirthday(entity.getBirthday());
        vo.setDescription(entity.getDescription());
        vo.setCv(entity.getCv());
        vo.setLikes(entity.getLikeCount() == null ? 0 : entity.getLikeCount());
        vo.setDislikes(entity.getDislikeCount() == null ? 0 : entity.getDislikeCount());
        return vo;
    }
}
