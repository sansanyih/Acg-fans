package com.example.acgdata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色出参：字段对齐前端 `Character`（id / name / voiceActor / image / likes）。
 *
 * <p>角色本体属于 character 模块，这里只是番剧详情里内嵌的一份快照：
 * 旧接口里这些角色本身就是硬编码在番剧详情里的，本次沿用同一份数据，
 * 由 Service 用一个只读目录（不在 Controller 里）拼装，等 character 模块接表后再改为关联查询。
 */
@Data
public class CharacterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String voiceActor;

    private String image;

    /** 点赞数 */
    private Integer likes;
}
