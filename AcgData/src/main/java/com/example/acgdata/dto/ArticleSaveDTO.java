package com.example.acgdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章新增 / 修改入参。
 * 作者信息（author / authorAvatar）由投稿方直接传入，与旧接口一致。
 */
@Data
public class ArticleSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "标题不能为空")
    @Size(max = 255, message = "标题不能超过 255 字")
    private String title;

    @Size(max = 500, message = "摘要不能超过 500 字")
    private String summary;

    @NotBlank(message = "正文不能为空")
    private String content;

    /** 封面地址，不传则用默认封面 */
    private String cover;

    /** 作者名（旧接口由请求体传入，这里保持同样的语义） */
    private String author;

    /** 作者头像，不传则用默认头像 */
    private String authorAvatar;

    /** 发布日期；新增时不传则取当前时间（旧逻辑为当天） */
    private LocalDateTime publishDate;
}
