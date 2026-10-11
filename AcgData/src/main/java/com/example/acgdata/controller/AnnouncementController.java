package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.AnnouncementSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.service.AnnouncementService;
import com.example.acgdata.vo.AnnouncementCommentVO;
import com.example.acgdata.vo.AnnouncementListVO;
import com.example.acgdata.vo.AnnouncementVO;
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
 * 公告接口。
 * 路径与返回结构与旧接口保持一致，只在最外层加了一层 Result。
 * 列表返回数组，详情返回单个对象；insert / update 返回保存后的完整对象。
 */
@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
@Validated
public class AnnouncementController {

    private final AnnouncementService announcementService;

    /** 公告列表：卡片字段数组，按发布日期倒序 */
    @GetMapping("/get")
    public Result<List<AnnouncementListVO>> getAnnouncementList() {
        return Result.ok(announcementService.listItem());
    }

    /** 公告详情：单个对象（含正文、lastEdit、comments） */
    @GetMapping("/get/{id}")
    public Result<AnnouncementVO> getAnnouncementById(@PathVariable Long id) {
        return Result.ok(announcementService.getVO(id));
    }

    @PostMapping("/insert")
    public Result<AnnouncementVO> insert(@Valid @RequestBody AnnouncementSaveDTO dto) {
        return Result.ok("新增成功", announcementService.saveAnnouncement(dto));
    }

    @PutMapping("/update/{id}")
    public Result<AnnouncementVO> update(@PathVariable Long id, @Valid @RequestBody AnnouncementSaveDTO dto) {
        return Result.ok("修改成功", announcementService.updateAnnouncement(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Map<String, String>> delete(@PathVariable Long id) {
        announcementService.removeAnnouncement(id);
        return Result.ok(Map.of("status", "success"));
    }

    /** 发表评论：返回新增的评论对象 */
    @PostMapping("/addComment/{id}")
    public Result<AnnouncementCommentVO> addComment(@PathVariable Long id, @Valid @RequestBody CommentSaveDTO dto) {
        return Result.ok("评论成功", announcementService.addComment(id, dto));
    }

    /** 点赞：返回 { success, likeCount } */
    @PostMapping("/like/{id}")
    public Result<Map<String, Object>> like(@PathVariable Long id) {
        return Result.ok(Map.of("success", true, "likeCount", announcementService.like(id)));
    }
}
