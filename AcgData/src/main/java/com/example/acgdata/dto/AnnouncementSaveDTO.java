package com.example.acgdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 公告新增 / 修改入参。
 * 注意：readCount / likeCount / publishDate / lastEdit 都不由前端传，
 * 由服务端维护（与旧接口行为一致：更新时忽略这些字段）。
 */
@Data
public class AnnouncementSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "公告标题不能为空")
    @Size(max = 200, message = "公告标题不能超过 200 字")
    private String title;

    private String content;

    @Size(max = 500, message = "公告摘要不能超过 500 字")
    private String summary;

    @Size(max = 500, message = "封面地址不能超过 500 字")
    private String cover;

    @Size(max = 50, message = "作者名不能超过 50 字")
    private String author;

    @Size(max = 500, message = "作者头像地址不能超过 500 字")
    private String authorAvatar;
}
