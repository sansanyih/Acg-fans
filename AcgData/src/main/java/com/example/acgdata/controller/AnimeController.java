package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.AnimeSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.service.AnimeService;
import com.example.acgdata.vo.AnimeDetailVO;
import com.example.acgdata.vo.AnimeItemVO;
import com.example.acgdata.vo.CommentVO;
import com.example.acgdata.vo.RatingVO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 番剧接口。
 *
 * <p>重构说明：旧实现把 18 部番剧、角色、评分、评论全部硬编码在 Controller 的内存 List 里，
 * 现已改为 Controller → Service → Mapper → MySQL 分层：
 * <ul>
 *     <li>路径与返回 JSON 结构与旧接口完全一致，只在最外层多了一层 {@code Result}；</li>
 *     <li>Controller 里不再有任何业务逻辑，只做参数校验与转发；</li>
 *     <li>写接口（insert / update / delete）由 SecurityConfig 的规则鉴权，路径拼接方式保持原样。</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/anime")
@RequiredArgsConstructor
@Validated
public class AnimeController {

    /** 列表类接口的默认 / 最大条数 */
    private static final int RANDOM_SIZE = 10;
    private static final int LATEST_SIZE = 10;
    private static final int RECENT_UPDATED_SIZE = 5;
    private static final int RANDOM_RATING_SIZE = 10;

    private final AnimeService animeService;

    /** 全部番剧（列表项：id / title / chineseTitle / image / description / tags / episodes / startDate / lastEdit） */
    @GetMapping("/get")
    public Result<List<AnimeItemVO>> getAnimeList() {
        return Result.ok(animeService.listAll());
    }

    /** 番剧详情（列表字段 + 制作信息 + 角色 + 观影记录 + 评论） */
    @GetMapping("/get/{id}")
    public Result<AnimeDetailVO> getAnimeById(@PathVariable Long id) {
        return Result.ok(animeService.getVO(id));
    }

    /** 随机推荐 10 部 */
    @GetMapping("/get/random")
    public Result<List<AnimeItemVO>> getRandomAnime() {
        return Result.ok(animeService.listRandom(RANDOM_SIZE));
    }

    /** 最新开播 10 部（按开播日期倒序） */
    @GetMapping("/get/latest")
    public Result<List<AnimeItemVO>> getLatestAnime() {
        return Result.ok(animeService.listLatest(LATEST_SIZE));
    }

    /** 即将开播的番剧（开播日期晚于今天，按日期升序） */
    @GetMapping("/get/upcoming")
    public Result<List<AnimeItemVO>> getUpcomingAnime() {
        return Result.ok(animeService.listUpcoming());
    }

    /** 最近编辑的 5 部 */
    @GetMapping("/get/recent-updated")
    public Result<List<AnimeItemVO>> getRecentUpdatedAnime() {
        return Result.ok(animeService.listRecentUpdated(RECENT_UPDATED_SIZE));
    }

    /** 随机标签 + 该标签下的番剧：{ "tag": "...", "anime": [...] } */
    @GetMapping("/get/random-tag")
    public Result<Map<String, Object>> getRandomTagAndAnime() {
        return Result.ok(animeService.randomTag());
    }

    /** 随机观影记录（评分）10 条，每条带所属番剧信息 */
    @GetMapping("/get/random-ratings")
    public Result<List<RatingVO>> getRandomRatings() {
        return Result.ok(animeService.randomRatings(RANDOM_RATING_SIZE));
    }

    /** 关键字搜索番剧（对外接口，统一 Result 包装） */
    @GetMapping("/search")
    public Result<List<AnimeItemVO>> searchAnimeByKeyword(@RequestParam String keyword) {
        return Result.ok(animeService.search(keyword));
    }

    /** 新增番剧 */
    @PostMapping("/insert")
    public Result<AnimeDetailVO> addAnime(@Valid @RequestBody AnimeSaveDTO dto) {
        return Result.ok("新增成功", animeService.saveAnime(dto));
    }

    /** 修改番剧 */
    @PutMapping("/update/{id}")
    public Result<AnimeDetailVO> updateAnime(@PathVariable Long id, @Valid @RequestBody AnimeSaveDTO dto) {
        return Result.ok("修改成功", animeService.updateAnime(id, dto));
    }

    /** 删除番剧（逻辑删除） */
    @DeleteMapping("/delete/{id}")
    public Result<Map<String, String>> deleteAnime(@PathVariable Long id) {
        animeService.removeAnime(id);
        return Result.ok(Map.of("status", "success"));
    }

    /** 发表评论，返回新增的评论 */
    @PostMapping("/addComment/{id}")
    public Result<CommentVO> addComment(@PathVariable Long id, @Valid @RequestBody CommentSaveDTO dto) {
        return Result.ok("评论成功", animeService.addComment(id, dto));
    }

}
