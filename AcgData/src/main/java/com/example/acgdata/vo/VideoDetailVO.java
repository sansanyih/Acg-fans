package com.example.acgdata.vo;

import com.example.acgdata.entity.Video;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频详情出参：字段与前端 `VideoDetail` 对齐
 * （thumbnail / title / author / authorAvatar / authorRole / authorLevel / authorBio /
 *   publishDate / description / externalLink / relatedTags / relatedAnime / comments）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VideoDetailVO extends VideoItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String authorRole;

    private Integer authorLevel;

    private String authorBio;

    private String description;

    private String externalLink;

    /** 由 video.related_tags（英文逗号分隔）拆出来 */
    private List<String> relatedTags = new ArrayList<>();

    /** 由 video.related_anime（英文逗号分隔的 id）转出来的对象数组 */
    private List<VideoAnimeItemVO> relatedAnime = new ArrayList<>();

    /** 评论由共享的 CommentService.listByTarget("video", id) 提供 */
    private List<CommentVO> comments = new ArrayList<>();

    public static VideoDetailVO from(Video video) {
        VideoDetailVO vo = new VideoDetailVO();
        fill(vo, video);
        vo.setAuthorRole(video.getAuthorRole());
        vo.setAuthorLevel(video.getAuthorLevel());
        vo.setAuthorBio(video.getAuthorBio());
        vo.setDescription(video.getDescription());
        vo.setExternalLink(video.getExternalLink());
        return vo;
    }
}
