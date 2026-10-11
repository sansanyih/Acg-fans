package com.example.acgdata.mapper.row;

import com.example.acgdata.vo.RatingVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 「观影记录 + 所属番剧」联表查询的投影行：在 {@link RatingVO} 之上额外带番剧 id / 标题，
 * 用于 `/api/anime/get/random-ratings`（前端 `RatingItem`）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RatingListRow extends RatingVO {

    private static final long serialVersionUID = 1L;

    private Long animeId;

    private String animeTitle;

    private String animeChineseTitle;
}
