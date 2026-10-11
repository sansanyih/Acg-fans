package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.dto.WeeklySaveDTO;
import com.example.acgdata.service.WeeklyReportService;
import com.example.acgdata.vo.WeeklyCommentVO;
import com.example.acgdata.vo.WeeklyItemVO;
import com.example.acgdata.vo.WeeklyReportVO;
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
 * 周报接口。
 * 路径与返回结构与旧接口保持一致，只在最外层加了一层 Result。
 * 列表返回数组，详情返回单个对象；insert / update 返回保存后的完整对象。
 */
@RestController
@RequestMapping("/api/weekly")
@RequiredArgsConstructor
@Validated
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    /** 周报列表：卡片字段数组，按发布日期倒序取最近 5 条 */
    @GetMapping("/get")
    public Result<List<WeeklyItemVO>> getReportList() {
        return Result.ok(weeklyReportService.listItem());
    }

    /** 周报详情：单个对象（含正文、lastEdit、comments） */
    @GetMapping("/get/{id}")
    public Result<WeeklyReportVO> getReportById(@PathVariable Long id) {
        return Result.ok(weeklyReportService.getVO(id));
    }

    @PostMapping("/insert")
    public Result<WeeklyReportVO> insert(@Valid @RequestBody WeeklySaveDTO dto) {
        return Result.ok("新增成功", weeklyReportService.saveReport(dto));
    }

    @PutMapping("/update/{id}")
    public Result<WeeklyReportVO> update(@PathVariable Long id, @Valid @RequestBody WeeklySaveDTO dto) {
        return Result.ok("修改成功", weeklyReportService.updateReport(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Map<String, String>> delete(@PathVariable Long id) {
        weeklyReportService.removeReport(id);
        return Result.ok(Map.of("status", "success"));
    }

    /** 发表评论：返回新增的评论对象 */
    @PostMapping("/addComment/{id}")
    public Result<WeeklyCommentVO> addComment(@PathVariable Long id, @Valid @RequestBody CommentSaveDTO dto) {
        return Result.ok("评论成功", weeklyReportService.addComment(id, dto));
    }

    /** 点赞：返回 { success, likeCount } */
    @PostMapping("/like/{id}")
    public Result<Map<String, Object>> like(@PathVariable Long id) {
        return Result.ok(Map.of("success", true, "likeCount", weeklyReportService.like(id)));
    }
}
