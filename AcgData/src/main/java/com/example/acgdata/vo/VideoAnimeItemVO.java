package com.example.acgdata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 视频详情里的「关联番剧」条目，字段与前端
 * `VideoDetail['relatedAnime'][number]` 对齐
 * （id / title / chineseTitle / image / description）。
 *
 * <p>说明：旧假数据里这几个字段是有值的，但 anime 模块本次不归本模块改造、
 * 且 video 表只冗余了番剧 id。为避免在编译期依赖尚未落库的 Anime 实体，
 * 这里只回填 id，其余字段待 anime 表 / AnimeService 就绪后在此回填即可。
 */
@Data
public class VideoAnimeItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String chineseTitle;

    private String image;

    private String description;

    public static VideoAnimeItemVO fromId(Long animeId) {
        VideoAnimeItemVO vo = new VideoAnimeItemVO();
        vo.setId(animeId);
        return vo;
    }
}
