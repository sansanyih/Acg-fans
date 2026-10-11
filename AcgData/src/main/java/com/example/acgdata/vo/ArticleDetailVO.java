package com.example.acgdata.vo;

import com.example.acgdata.entity.Article;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章详情出参：字段与前端 `ArticleDetail` 对齐
 * （在 `ArticleItem` 基础上多出 content / lastEdit / comments）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleDetailVO extends ArticleItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 正文 */
    private String content;

    /**
     * 最后一次编辑时间。
     * 旧接口输出的是 `2026年2月13日 18:26:13` 这类中文字符串，
     * 这里保持同样的格式，与公告 / 周报模块的 lastEdit 保持一致。
     */
    @JsonFormat(pattern = "yyyy年M月dd日 HH:mm:ss")
    private LocalDateTime lastEdit;

    /** 评论由共享的 CommentService.listByTarget("article", id) 提供 */
    private List<CommentVO> comments = new ArrayList<>();

    public static ArticleDetailVO from(Article article) {
        ArticleDetailVO vo = new ArticleDetailVO();
        fill(vo, article);
        vo.setContent(article.getContent());
        // 旧接口的 lastEdit 语义是「最后一次编辑时间」
        vo.setLastEdit(article.getUpdateTime() == null ? article.getCreateTime() : article.getUpdateTime());
        return vo;
    }

    public static ArticleDetailVO from(Article article, List<CommentVO> comments) {
        ArticleDetailVO vo = from(article);
        vo.setComments(comments == null ? new ArrayList<>() : comments);
        return vo;
    }
}
