package com.example.acgdata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 全站搜索 - 文章条目，字段对齐前端 `SearchArticleItem`。
 */
@Data
public class SearchArticleItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String cover;
    private String summary;
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime publishDate;

    /** 判别字段，固定 article */
    private String type = "article";
}
