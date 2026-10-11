package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.example.acgdata.service.EditStatisticsService;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class EditStatisticsController {

    // 获取所有编辑统计数据
    @GetMapping("/edits")
    public Map<String, Map<String, Integer>> getEditStatistics() {
        return EditStatisticsService.getAllEditStatistics();
    }
    
    // 获取指定日期范围的编辑统计数据
    @GetMapping("/edits/range")
    public Map<String, Map<String, Integer>> getEditStatisticsByRange(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return EditStatisticsService.getEditStatistics(startDate, endDate);
    }
    
    // 获取最近30天的编辑统计数据
    @GetMapping("/edits/recent")
    public Map<String, Map<String, Integer>> getRecentEditStatistics() {
        // 计算30天前的日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        String startDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        String endDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return EditStatisticsService.getEditStatistics(startDate, endDate);
    }
}