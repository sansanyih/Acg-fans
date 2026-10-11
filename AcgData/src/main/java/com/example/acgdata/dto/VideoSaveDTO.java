package com.example.acgdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 视频新增 / 修改入参。
 * 作者相关信息由投稿方直接传入，与旧接口一致。
 */
@Data
public class VideoSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "标题不能为空")
    @Size(max = 255, message = "标题不能超过 255 字")
    private String title;

    /** 封面图，不传则用默认封面 */
    private String thumbnail;

    private String author;

    /** 作者头像，不传则用默认头像 */
    private String authorAvatar;

    /** 作者身份，不传则用默认值 */
    private String authorRole;

    /** 作者等级，不传按 1 处理 */
    private Integer authorLevel;

    /** 作者简介，不传则用默认文案 */
    private String authorBio;

    private String description;

    private String externalLink;

    /** 关联标签名列表 */
    private List<String> relatedTags;

    /** 关联番剧 id 列表（旧接口传的是对象数组，这里只保留 id） */
    private List<Long> relatedAnime;

    /** 发布时间；新增时不传则取当前时间 */
    private LocalDateTime publishDate;
}
