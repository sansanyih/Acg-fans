package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.dto.VideoSaveDTO;
import com.example.acgdata.service.VideoService;
import com.example.acgdata.vo.CommentVO;
import com.example.acgdata.vo.VideoDetailVO;
import com.example.acgdata.vo.VideoItemVO;
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频接口。
 * 路径与 data 结构与旧接口保持一致，只在最外层加了一层 Result。
 * （旧实现里的内存假数据已迁移到 MySQL，见 db/schema/21_video.sql 与 db/data/21_video.sql）
 */
@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
@Validated
public class VideoController {

    private final VideoService videoService;

    /** 列表（按发布时间倒序）：数组，字段同前端 VideoItem */
    @GetMapping("/get")
    public Result<List<VideoItemVO>> getLatestVideos() {
        return Result.ok(videoService.listAll());
    }

    /** 详情：单个对象，含 relatedTags / relatedAnime / comments */
    @GetMapping("/get/{id}")
    public Result<VideoDetailVO> getVideoById(@PathVariable Long id) {
        return Result.ok(videoService.getDetail(id));
    }

    /**
     * 搜索：数组。
     * 具体实现在 VideoService#search，SearchController 也复用该方法。
     */
    @GetMapping("/search")
    public Result<List<VideoItemVO>> searchVideo(@RequestParam String keyword) {
        return Result.ok(videoService.search(keyword));
    }

    /** 新增：返回保存后的完整对象 */
    @PostMapping("/insert")
    public Result<VideoDetailVO> insert(@Valid @RequestBody VideoSaveDTO dto) {
        return Result.ok("新增成功", videoService.saveVideo(dto));
    }

    /** 修改：返回修改后的完整对象（id / publishDate 不允许被改，与旧逻辑一致） */
    @PutMapping("/update/{id}")
    public Result<VideoDetailVO> update(@PathVariable Long id, @Valid @RequestBody VideoSaveDTO dto) {
        return Result.ok("修改成功", videoService.updateVideo(id, dto));
    }

    /** 删除：旧接口返回 { "success": true, "message": "删除成功" }，这里保持同样的结构 */
    @DeleteMapping("/delete/{id}")
    public Result<Map<String, Object>> delete(@PathVariable Long id) {
        videoService.removeVideo(id);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("success", true);
        data.put("message", "删除成功");
        return Result.ok(data);
    }

    /** 发表评论：返回新增的评论对象 */
    @PostMapping("/addComment/{id}")
    public Result<CommentVO> addComment(@PathVariable Long id, @Valid @RequestBody CommentSaveDTO dto) {
        return Result.ok("评论成功", videoService.addComment(id, dto));
    }

}
