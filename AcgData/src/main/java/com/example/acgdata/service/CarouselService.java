package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.CarouselSaveDTO;
import com.example.acgdata.entity.Carousel;
import com.example.acgdata.vo.CarouselVO;

import java.util.List;

/**
 * 首页轮播图服务。
 */
public interface CarouselService extends IService<Carousel> {

    /** 全部轮播图（按 sort、id 升序） */
    List<CarouselVO> listAll();

    CarouselVO getVO(Long id);

    CarouselVO saveCarousel(CarouselSaveDTO dto);

    CarouselVO updateCarousel(Long id, CarouselSaveDTO dto);

    /** 逻辑删除，查不到抛业务异常 */
    void removeCarousel(Long id);
}
