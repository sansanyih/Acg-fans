package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/carousel")
@CrossOrigin(origins = "*")
public class CarouselController {

    private List<Map<String, Object>> carouselList = new ArrayList<>();

    public CarouselController() {
        // 初始化轮播图数据
        String baseUrl = "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/";
        List<Map<String, Object>> initCarousel = Arrays.asList(
                Map.of("id", 1, "url", baseUrl + "1.png", "title", "轮播图1"),
                Map.of("id", 2, "url", baseUrl + "2.png", "title", "轮播图2"),
                Map.of("id", 3, "url", baseUrl + "4.jpg", "title", "轮播图3")
        );
        carouselList.addAll(initCarousel);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getCarousel() {
        return carouselList;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getCarouselById(@PathVariable int id) {
        return carouselList.stream()
                .filter(item -> (int) item.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/insert")
    public Map<String, Object> addCarousel(@RequestBody Map<String, Object> carouselData) {
        Map<String, Object> newCarousel = new HashMap<>(carouselData);
        newCarousel.put("id", carouselList.size() + 1);
        carouselList.add(newCarousel);
        return newCarousel;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateCarousel(@PathVariable int id, @RequestBody Map<String, Object> carouselData) {
        for (Map<String, Object> carousel : carouselList) {
            if ((int) carousel.get("id") == id) {
                carousel.putAll(carouselData);
                return carousel;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteCarousel(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        boolean removed = carouselList.removeIf(carousel -> (int) carousel.get("id") == id);
        response.put("status", removed ? "success" : "error");
        return response;
    }
}
