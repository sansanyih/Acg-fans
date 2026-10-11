package com.example.acgdata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 全站搜索 - 视频条目，字段对齐前端 `SearchVideoItem`。
 */
@Data
public class SearchVideoItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String thumbnail;
    private String description;
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDate;

    /** 判别字段，固定 video */
    private String type = "video";
}
