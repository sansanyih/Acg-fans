package com.example.acgdata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 全站搜索 - 番剧条目，字段对齐前端 `SearchAnimeItem`。
 */
@Data
public class SearchAnimeItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String chineseTitle;
    private String image;
    private String description;

    /** 判别字段，固定 anime */
    private String type = "anime";
}
