package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.CarouselSaveDTO;
import com.example.acgdata.entity.Carousel;
import com.example.acgdata.mapper.CarouselMapper;
import com.example.acgdata.service.CarouselService;
import com.example.acgdata.vo.CarouselVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页轮播图服务实现。
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Override
    public List<CarouselVO> listAll() {
        return list(Wrappers.<Carousel>lambdaQuery()
                .orderByAsc(Carousel::getSort)
                .orderByAsc(Carousel::getId))
                .stream()
                .map(CarouselVO::from)
                .toList();
    }

    @Override
    public CarouselVO getVO(Long id) {
        return CarouselVO.from(getExisting(id));
    }

    @Override
    public CarouselVO saveCarousel(CarouselSaveDTO dto) {
        Carousel carousel = new Carousel();
        apply(carousel, dto);
        save(carousel);
        return CarouselVO.from(carousel);
    }

    @Override
    public CarouselVO updateCarousel(Long id, CarouselSaveDTO dto) {
        Carousel carousel = getExisting(id);
        apply(carousel, dto);
        updateById(carousel);
        return CarouselVO.from(carousel);
    }

    @Override
    public void removeCarousel(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic）
        removeById(id);
    }

    private Carousel getExisting(Long id) {
        Carousel carousel = getById(id);
        if (carousel == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return carousel;
    }

    private void apply(Carousel carousel, CarouselSaveDTO dto) {
        carousel.setUrl(dto.getUrl());
        carousel.setTitle(dto.getTitle());
        carousel.setSort(dto.getSort() == null ? 0 : dto.getSort());
    }
}
