package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.CarouselSaveDTO;
import com.example.acgdata.service.CarouselService;
import com.example.acgdata.vo.CarouselVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 首页轮播图接口。
 * 路径与返回结构与旧接口保持一致，只在最外层加了一层 Result。
 * 列表返回数组，详情返回单个对象（字段只有 id / url / title）。
 */
@RestController
@RequestMapping("/api/carousel")
@RequiredArgsConstructor
@Validated
public class CarouselController {

    private final CarouselService carouselService;

    @GetMapping("/get")
    public Result<List<CarouselVO>> getCarousel() {
        return Result.ok(carouselService.listAll());
    }

    @GetMapping("/get/{id}")
    public Result<CarouselVO> getCarouselById(@PathVariable Long id) {
        return Result.ok(carouselService.getVO(id));
    }

    @PostMapping("/insert")
    public Result<CarouselVO> insert(@Valid @RequestBody CarouselSaveDTO dto) {
        return Result.ok("新增成功", carouselService.saveCarousel(dto));
    }

    @PutMapping("/update/{id}")
    public Result<CarouselVO> update(@PathVariable Long id, @Valid @RequestBody CarouselSaveDTO dto) {
        return Result.ok("修改成功", carouselService.updateCarousel(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Map<String, String>> delete(@PathVariable Long id) {
        carouselService.removeCarousel(id);
        return Result.ok(Map.of("status", "success"));
    }
}
