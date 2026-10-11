package com.example.acgdata.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 随机评分出参：字段对齐前端 `RatingItem`
 * （在 {@link ViewingRecordVO} 之上补 animeId / animeTitle / animeChineseTitle，
 * 前端拿到后能直接展示「《番剧名》」）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RatingVO extends ViewingRecordVO {

    private static final long serialVersionUID = 1L;

    private Long animeId;

    private String animeTitle;

    private String animeChineseTitle;
}
