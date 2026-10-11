package com.example.acgdata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 全站搜索 - 角色条目，字段对齐前端 `SearchCharacterItem`。
 */
@Data
public class SearchCharacterItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String chineseName;
    private String image;
    private String description;

    /** 判别字段，固定 character */
    private String type = "character";
}
