package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.service.EditStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * 编辑统计接口：返回结构保持「日期 -> {anime, article, video, character}」不变。
 */
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class EditStatisticsController {

    private final EditStatisticService editStatisticService;

    @GetMapping("/edits")
    public Result<Map<String, Map<String, Integer>>> getAll() {
        return Result.ok(editStatisticService.getAll());
    }

    @GetMapping("/edits/range")
    public Result<Map<String, Map<String, Integer>>> getByRange(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return Result.ok(editStatisticService.getRange(parseDate(startDate), parseDate(endDate)));
    }

    /** 最近 30 天 */
    @GetMapping("/edits/recent")
    public Result<Map<String, Map<String, Integer>>> getRecent() {
        LocalDate today = LocalDate.now();
        return Result.ok(editStatisticService.getRange(today.minusDays(30), today));
    }

    private LocalDate parseDate(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "日期格式应为 yyyy-MM-dd");
        }
    }
}
