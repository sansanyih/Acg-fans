package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.FeedSaveDTO;
import com.example.acgdata.service.FeedService;
import com.example.acgdata.vo.FeedDeleteVO;
import com.example.acgdata.vo.FeedVO;
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

/**
 * 动态接口。
 *
 * <p>路径与返回结构与旧接口保持一致，只在最外层加了一层 {@link Result}。
 */
@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
@Validated
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/get")
    public Result<List<FeedVO>> getFeedList() {
        return Result.ok(feedService.listAll());
    }

    @GetMapping("/get/{id}")
    public Result<FeedVO> getFeedById(@PathVariable Long id) {
        return Result.ok(feedService.getVO(id));
    }

    @GetMapping("/search")
    public Result<List<FeedVO>> searchFeed(@RequestParam String keyword) {
        return Result.ok(feedService.search(keyword));
    }

    @PostMapping("/insert")
    public Result<FeedVO> insert(@Valid @RequestBody FeedSaveDTO dto) {
        return Result.ok("新增成功", feedService.saveFeed(dto));
    }

    @PutMapping("/update/{id}")
    public Result<FeedVO> update(@PathVariable Long id, @Valid @RequestBody FeedSaveDTO dto) {
        return Result.ok("修改成功", feedService.updateFeed(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public Result<FeedDeleteVO> delete(@PathVariable Long id) {
        feedService.removeFeed(id);
        // 旧接口：{ "success": true, "message": "删除成功" }
        return Result.ok(FeedDeleteVO.removed("删除成功"));
    }
}
