package com.example.acgdata.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EditStatisticsService {
    // 存储每日编辑统计数据，格式：日期 -> 类型 -> 数量
    private static final Map<String, Map<String, Integer>> editStatistics = new ConcurrentHashMap<>();
    
    // 初始化一些模拟数据
    static {
        // 3月16日到4月14日的模拟数据
        String[] dates = {
            "2026-03-16", "2026-03-17", "2026-03-18", "2026-03-19", "2026-03-20",
            "2026-03-21", "2026-03-22", "2026-03-23", "2026-03-24", "2026-03-25",
            "2026-03-26", "2026-03-27", "2026-03-28", "2026-03-29", "2026-03-30",
            "2026-03-31", "2026-04-01", "2026-04-02", "2026-04-03", "2026-04-04",
            "2026-04-05", "2026-04-06", "2026-04-07", "2026-04-08", "2026-04-09",
            "2026-04-10", "2026-04-11", "2026-04-12", "2026-04-13", "2026-04-14"
        };
        
        Random random = new Random();
        
        for (String date : dates) {
            Map<String, Integer> typeCounts = new LinkedHashMap<>();
            typeCounts.put("anime", random.nextInt(100) + 20);   // 番剧：20-119
            typeCounts.put("article", random.nextInt(150) + 30); // 文章：30-179
            typeCounts.put("video", random.nextInt(50) + 10);    // 视频：10-59
            typeCounts.put("character", random.nextInt(80) + 15); // 角色：15-94
            editStatistics.put(date, typeCounts);
        }
    }
    
    // 增加编辑计数
    public static void incrementEditCount(String type) {
        String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
        editStatistics.computeIfAbsent(today, k -> new LinkedHashMap<>())
                      .compute(type, (k, v) -> v == null ? 1 : v + 1);
    }
    
    // 获取指定日期范围的编辑统计数据
    public static Map<String, Map<String, Integer>> getEditStatistics(String startDate, String endDate) {
        Map<String, Map<String, Integer>> result = new LinkedHashMap<>();
        
        // 遍历所有日期，筛选在指定范围内的
        for (Map.Entry<String, Map<String, Integer>> entry : editStatistics.entrySet()) {
            String date = entry.getKey();
            if ((startDate == null || date.compareTo(startDate) >= 0) &&
                (endDate == null || date.compareTo(endDate) <= 0)) {
                result.put(date, entry.getValue());
            }
        }
        
        return result;
    }
    
    // 获取所有编辑统计数据
    public static Map<String, Map<String, Integer>> getAllEditStatistics() {
        return new LinkedHashMap<>(editStatistics);
    }
}