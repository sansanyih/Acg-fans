package com.example.acgdata.vo;

import com.example.acgdata.entity.Video;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 视频列表项出参：字段与前端 `VideoItem` 逐字段对齐
 * （id / thumbnail / title / author / authorAvatar / publishDate）。
 *
 * <p>SearchController 也依赖本类型，故不可改名、不可删字段。
 * `description` / `duration` / `views` / `likes` 不是前端 `VideoItem` 的字段，
 * 但旧 `GET /api/video/search` 与 `GET /api/search/all` 的结果里一直带着它们，
 * 因此这里保留原样输出（旧实现这几个值基本都是 null）。
 */
@Data
public class VideoItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String thumbnail;

    private String title;

    private String author;

    private String authorAvatar;

    /** 旧接口格式：yyyy-MM-dd HH:mm:ss */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDate;

    /** 仅搜索接口会带（旧实现恒为 null） */
    private String description;

    /** 仅搜索接口会带（旧实现恒为 null） */
    private String duration;

    /** 仅搜索接口会带（旧实现恒为 null） */
    private Integer views;

    /** 仅搜索接口会带（旧实现恒为 null） */
    private Integer likes;

    public static VideoItemVO from(Video video) {
        VideoItemVO vo = new VideoItemVO();
        fill(vo, video);
        return vo;
    }

    /** 搜索结果的字段集（比列表多 description / duration / views / likes） */
    public static VideoItemVO fromSearch(Video video) {
        VideoItemVO vo = from(video);
        vo.setDescription(video.getDescription());
        vo.setDuration(video.getDuration());
        vo.setViews(video.getViewCount());
        vo.setLikes(video.getLikeCount());
        return vo;
    }

    protected static void fill(VideoItemVO vo, Video video) {
        vo.setId(video.getId());
        vo.setThumbnail(video.getThumbnail());
        vo.setTitle(video.getTitle());
        vo.setAuthor(video.getAuthor());
        vo.setAuthorAvatar(video.getAuthorAvatar());
        vo.setPublishDate(video.getPublishDate());
    }
}
