package com.example.acgdata.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 全站搜索聚合结果，字段对齐前端 `SearchResult`。
 */
@Data
public class SearchResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<SearchAnimeItemVO> anime = new ArrayList<>();

    private List<SearchArticleItemVO> article = new ArrayList<>();

    private List<SearchVideoItemVO> video = new ArrayList<>();

    private List<SearchCharacterItemVO> character = new ArrayList<>();
}
