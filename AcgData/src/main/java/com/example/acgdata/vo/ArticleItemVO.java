package com.example.acgdata.vo;

import com.example.acgdata.entity.Article;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章列表项出参：字段与前端 `ArticleItem` 逐字段对齐
 * （id / title / summary / author / authorAvatar / publishDate / readCount / likeCount / cover）。
 *
 * <p>SearchController 也依赖本类型，故不可改名、不可删字段。
 * `type` 不是前端 `ArticleItem` 的字段，但旧 `GET /api/article/search` 与
 * `GET /api/search/all` 的结果里一直带着它，这里保持原样输出。
 */
@Data
public class ArticleItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String summary;

    private String author;

    private String authorAvatar;

    /** 旧接口格式：yyyy-MM-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime publishDate;

    private Integer readCount;

    private Integer likeCount;

    private String cover;

    /** 旧接口固定值：article */
    private String type = "article";

    public static ArticleItemVO from(Article article) {
        ArticleItemVO vo = new ArticleItemVO();
        fill(vo, article);
        return vo;
    }

    /** 字段映射集中在这里，列表 / 详情 / 搜索共用 */
    protected static void fill(ArticleItemVO vo, Article article) {
        vo.setId(article.getId());
        vo.setTitle(article.getTitle());
        vo.setSummary(article.getSummary());
        vo.setAuthor(article.getAuthor());
        vo.setAuthorAvatar(article.getAuthorAvatar());
        vo.setPublishDate(article.getPublishDate());
        vo.setReadCount(article.getReadCount());
        vo.setLikeCount(article.getLikeCount());
        vo.setCover(article.getCover());
    }
}
