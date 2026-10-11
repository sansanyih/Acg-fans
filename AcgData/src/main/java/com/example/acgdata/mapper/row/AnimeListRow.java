package com.example.acgdata.mapper.row;

import com.example.acgdata.vo.AnimeItemVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 「番剧 + 标签」联表查询的投影行：一个番剧有几个标签就有几行，
 * 由 Service 按 animeId 分组后拼成 {@link AnimeItemVO#getTags()}。
 *
 * <p>放在 mapper.row 包，只用于承载查询结果，不对外暴露。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnimeListRow extends AnimeItemVO {

    private static final long serialVersionUID = 1L;

    /** LEFT JOIN 出来的标签名，可能为 null */
    private String tagName;
}
