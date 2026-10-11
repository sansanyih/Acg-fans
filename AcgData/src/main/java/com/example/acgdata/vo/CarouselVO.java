package com.example.acgdata.vo;

import com.example.acgdata.entity.Carousel;
import lombok.Data;

import java.io.Serializable;

/**
 * 轮播图出参：字段与前端 `CarouselItem` 一致（id / url / title）。
 */
@Data
public class CarouselVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 图片地址 */
    private String url;

    private String title;

    public static CarouselVO from(Carousel carousel) {
        CarouselVO vo = new CarouselVO();
        vo.setId(carousel.getId());
        vo.setUrl(carousel.getUrl());
        vo.setTitle(carousel.getTitle());
        return vo;
    }
}
