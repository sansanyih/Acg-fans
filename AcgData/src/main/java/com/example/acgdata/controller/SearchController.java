package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    private final AnimeController animeController;
    private final ArticleController articleController;
    private final VideoController videoController;
    private final CharacterController characterController;

    public SearchController(AnimeController animeController, ArticleController articleController, VideoController videoController, CharacterController characterController) {
        this.animeController = animeController;
        this.articleController = articleController;
        this.videoController = videoController;
        this.characterController = characterController;
    }

    @GetMapping("/api/search/all")
    public Map<String, List<Map<String, Object>>> searchAll(@RequestParam String keyword) {
        Map<String, List<Map<String, Object>>> searchResults = new LinkedHashMap<>();

        // 搜索番剧
        List<Map<String, Object>> animeResults = animeController.searchAnime(keyword);
        searchResults.put("anime", animeResults);

        // 搜索文章
        List<Map<String, Object>> articleResults = articleController.searchArticle(keyword);
        searchResults.put("article", articleResults);

        // 搜索视频
        List<Map<String, Object>> videoResults = videoController.searchVideo(keyword);
        searchResults.put("video", videoResults);

        // 搜索角色
        List<Map<String, Object>> characterResults = characterController.searchCharacter(keyword);
        searchResults.put("character", characterResults);

        return searchResults;
    }
}