package com.example.acgdata.vo;

import com.example.acgdata.entity.Anime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 番剧列表项出参：字段与前端 `AnimeItem` 完全一致
 * （id / title / chineseTitle / image / description / tags / episodes / startDate / lastEdit）。
 *
 * <p>被 `/api/anime/get`、`/get/random`、`/get/latest`、`/get/upcoming`、
 * `/get/recent-updated`、`/get/random-tag`、`/search` 以及跨模块的
 * `SearchController` 共用，字段名不能改。
 */
@Data
public class AnimeItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 旧接口固定返回的标记位，恒为 anime */
    public static final String TYPE = "anime";

    private Long id;

    private String title;

    private String chineseTitle;

    private String image;

    private String description;

    /** 标签名列表，来自 anime_tag / tag 关联 */
    private List<String> tags;

    private Integer episodes;

    /** 开播日期，固定输出 yyyy-MM-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /** 最近编辑时间，固定输出 yyyy-MM-dd HH:mm:ss（不要依赖默认的 ISO 格式） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastEdit;

    /** 点赞数（旧接口没有，前端也不强依赖，但保持与其它模块一致） */
    private Integer likeCount;

    /** 内容类型标记，旧搜索接口返回过该字段，保留以免前端取值落空 */
    private String type;

    /**
     * 实体 -> 列表项 VO（不含标签，标签由 Service 批量补齐；不含 userId 等敏感字段）。
     */
    public static AnimeItemVO of(Anime anime) {
        AnimeItemVO vo = new AnimeItemVO();
        vo.setId(anime.getId());
        vo.setTitle(anime.getTitle());
        vo.setChineseTitle(anime.getChineseTitle());
        vo.setImage(anime.getImage());
        vo.setDescription(anime.getDescription());
        vo.setEpisodes(anime.getEpisodes());
        vo.setStartDate(anime.getStartDate());
        vo.setLastEdit(anime.getUpdateTime());
        vo.setLikeCount(anime.getLikeCount());
        vo.setType(TYPE);
        return vo;
    }
}
