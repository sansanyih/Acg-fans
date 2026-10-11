package com.example.acgdata.vo;

import com.example.acgdata.entity.Link;
import lombok.Data;

import java.io.Serializable;

/**
 * 友情链接出参：字段名对齐前端 `LinkItem`
 * （接口里 url 是图标、link 才是跳转地址，历史命名如此，这里用 VO 做翻译）。
 */
@Data
public class LinkVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    /** 图标地址 */
    private String url;

    /** 跳转地址 */
    private String link;

    public static LinkVO from(Link link) {
        LinkVO vo = new LinkVO();
        vo.setId(link.getId());
        vo.setName(link.getName());
        vo.setUrl(link.getIconUrl());
        vo.setLink(link.getTargetUrl());
        return vo;
    }
}
