package com.example.acgdata.vo;

import com.example.acgdata.entity.Feed;
import lombok.Data;

import java.io.Serializable;

/**
 * 动态出参：字段名逐个对齐前端 `FeedItem`
 * （AcgWeb/src/api/modules/feed/interface.ts）：id / userAvatar / username / time / content / link。
 */
@Data
public class FeedVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userAvatar;

    private String username;

    /** 发布时间文案（如「2026年04月10日」「2天前」「刚刚」） */
    private String time;

    private String content;

    private String link;

    public static FeedVO from(Feed entity) {
        FeedVO vo = new FeedVO();
        vo.setId(entity.getId());
        vo.setUserAvatar(entity.getUserAvatar());
        vo.setUsername(entity.getUsername());
        vo.setTime(entity.getTimeLabel());
        vo.setContent(entity.getContent());
        vo.setLink(entity.getLink());
        return vo;
    }
}
