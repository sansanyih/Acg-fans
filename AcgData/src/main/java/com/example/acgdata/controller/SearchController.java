package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.service.AnimeService;
import com.example.acgdata.service.ArticleService;
import com.example.acgdata.service.CharacterService;
import com.example.acgdata.service.VideoService;
import com.example.acgdata.vo.AnimeItemVO;
import com.example.acgdata.vo.ArticleItemVO;
import com.example.acgdata.vo.CharacterItemVO;
import com.example.acgdata.vo.SearchAnimeItemVO;
import com.example.acgdata.vo.SearchArticleItemVO;
import com.example.acgdata.vo.SearchCharacterItemVO;
import com.example.acgdata.vo.SearchResultVO;
import com.example.acgdata.vo.SearchVideoItemVO;
import com.example.acgdata.vo.VideoItemVO;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全站搜索聚合接口。
 *
 * <p>这里只依赖各模块的 Service（不再像旧实现那样注入别的 Controller），
 * 由本层负责把各模块的列表项裁剪成前端 `SearchResult` 需要的字段，
 * 并补上 `type` 判别字段。
 */
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Validated
public class SearchController {

    private final AnimeService animeService;
    private final ArticleService articleService;
    private final VideoService videoService;
    private final CharacterService characterService;

    @GetMapping("/all")
    public Result<SearchResultVO> searchAll(@RequestParam @NotBlank(message = "搜索关键字不能为空") String keyword) {
        SearchResultVO result = new SearchResultVO();
        result.setAnime(animeService.search(keyword).stream().map(SearchController::toAnimeItem).toList());
        result.setArticle(articleService.search(keyword).stream().map(SearchController::toArticleItem).toList());
        result.setVideo(videoService.search(keyword).stream().map(SearchController::toVideoItem).toList());
        result.setCharacter(characterService.search(keyword).stream().map(SearchController::toCharacterItem).toList());
        return Result.ok(result);
    }

    private static SearchAnimeItemVO toAnimeItem(AnimeItemVO source) {
        SearchAnimeItemVO vo = new SearchAnimeItemVO();
        vo.setId(source.getId());
        vo.setTitle(source.getTitle());
        vo.setChineseTitle(source.getChineseTitle());
        vo.setImage(source.getImage());
        vo.setDescription(source.getDescription());
        return vo;
    }

    private static SearchArticleItemVO toArticleItem(ArticleItemVO source) {
        SearchArticleItemVO vo = new SearchArticleItemVO();
        vo.setId(source.getId());
        vo.setTitle(source.getTitle());
        vo.setCover(source.getCover());
        vo.setSummary(source.getSummary());
        vo.setAuthor(source.getAuthor());
        vo.setPublishDate(source.getPublishDate());
        return vo;
    }

    private static SearchVideoItemVO toVideoItem(VideoItemVO source) {
        SearchVideoItemVO vo = new SearchVideoItemVO();
        vo.setId(source.getId());
        vo.setTitle(source.getTitle());
        vo.setThumbnail(source.getThumbnail());
        vo.setDescription(source.getDescription());
        vo.setAuthor(source.getAuthor());
        vo.setPublishDate(source.getPublishDate());
        return vo;
    }

    private static SearchCharacterItemVO toCharacterItem(CharacterItemVO source) {
        SearchCharacterItemVO vo = new SearchCharacterItemVO();
        vo.setId(source.getId());
        vo.setName(source.getName());
        // 角色表里 name 存的就是中文名，前端搜索项同时要 name 与 chineseName
        vo.setChineseName(source.getName());
        vo.setImage(source.getImage());
        vo.setDescription(source.getDescription());
        return vo;
    }
}
