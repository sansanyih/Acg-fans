package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private List<Map<String, Object>> feedList = new ArrayList<>();

    public FeedController() {
        initFeedData();
    }

    private void initFeedData() {
        Map<String, Object> feed1 = new LinkedHashMap<>();
        feed1.put("id", 1);
        feed1.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/Feed-Aniplex.jpg");
        feed1.put("username", "Aniplex_Official");
        feed1.put("time", "2026年04月10日");
        feed1.put("content", "分享图片你的人格诊断是？");
        feed1.put("link", "https://www.bilibili.com/opus/1189617666584739889?spm_id_from=333.1387.0.0");
        feedList.add(feed1);

        Map<String, Object> feed2 = new LinkedHashMap<>();
        feed2.put("id", 4);
        feed2.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/Feed-fanshi.jpg");
        feed2.put("username", "泛式");
        feed2.put("time", "2026年04月6日");
        feed2.put("content", "泛式队长，完结吐槽！");
        feed2.put("link", "https://t.bilibili.com/1188147232997638194?spm_id_from=333.1387.0.0");
        feedList.add(feed2);

        Map<String, Object> feed3 = new LinkedHashMap<>();
        feed3.put("id", 2);
        feed3.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-smbb.jpg");
        feed3.put("username", "数码宝贝官方");
        feed3.put("time", "2026年04月02日");
        feed3.put("content", "自2006年4月2日播出的动画『数码宝贝 拯救队』今天迎来了第20周年的纪念日");
        feed3.put("link", "https://www.bilibili.com/opus/1186544847255765013?spm_id_from=333.1387.0.0");
        feedList.add(feed3);

        Map<String, Object> feed4 = new LinkedHashMap<>();
        feed4.put("id", 3);
        feed4.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-toei.jpg");
        feed4.put("username", "东映动画");
        feed4.put("time", "2026年04月09日");
        feed4.put("content", "今天是《境界触发者》中 迅悠一的生日哦！");
        feed4.put("link", "https://www.bilibili.com/opus/1189111519670960153?spm_id_from=333.1387.0.0");
        feedList.add(feed4);

        Map<String, Object> feed5 = new LinkedHashMap<>();
        feed5.put("id", 5);
        feed5.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-gbc.png");
        feed5.put("username", "少女乐队的呐喊官方");
        feed5.put("time", "2026年04月07日");
        feed5.put("content", "【特别新品❗「毛绒指偶公仔」实拍首发");
        feed5.put("link", "https://t.bilibili.com/1188494863327297545?spm_id_from=333.1387.0.0");
        feedList.add(feed5);

        Map<String, Object> feed6 = new LinkedHashMap<>();
        feed6.put("id", 6);
        feed6.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-xrht.jpg");
        feed6.put("username", "夏日幻听MCE");
        feed6.put("time", "2天前");
        feed6.put("content", "4月新番《#关于邻家的天使大人不知不觉把我惯成了废人这档子事#》第二季 02 已更新！");
        feed6.put("link", "https://t.bilibili.com/1189717782239903745?spm_id_from=333.1387.0.0");
        feedList.add(feed6);

        Map<String, Object> feed7 = new LinkedHashMap<>();
        feed7.put("id", 7);
        feed7.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/Feed-Aniplex.jpg");
        feed7.put("username", "Aniplex_Official");
        feed7.put("time", "2天前");
        feed7.put("content", "【海外情报】《主播女孩重度依赖》角色KV 超绝最可爱天使酱 ");
        feed7.put("link", "https://www.bilibili.com/opus/1189235214729084933?spm_id_from=333.1387.0.0");
        feedList.add(feed7);

        Map<String, Object> feed8 = new LinkedHashMap<>();
        feed8.put("id", 8);
        feed8.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-152.jpg");
        feed8.put("username", "瓶子君152");
        feed8.put("time", "2026年03月28日");
        feed8.put("content", "没有人会记得按时发布的垃圾视频");
        feed8.put("link", "https://t.bilibili.com/1184785343053824005?spm_id_from=333.1387.0.0");
        feedList.add(feed8);

        Map<String, Object> feed9 = new LinkedHashMap<>();
        feed9.put("id", 9);
        feed9.put("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/Feed-Aniplex.jpg");
        feed9.put("username", "Aniplex_Official");
        feed9.put("time", "2026年03月31日");
        feed9.put("content", "魔法少女小圆 × 东京晴空塔「月夜华尔兹」");
        feed9.put("link", "https://www.bilibili.com/opus/1185779741794238484?spm_id_from=333.1387.0.0");
        feedList.add(feed9);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getFeedList() {
        return feedList.stream()
                .sorted((a, b) -> {
                    String timeA = (String) a.get("time");
                    String timeB = (String) b.get("time");
                    
                    // 处理不同时间格式的排序
                    try {
                        // 处理日期格式：2026年04月10日
                        if (timeA.contains("年") && timeA.contains("月") && timeA.contains("日")) {
                            String dateStrA = timeA.replace("年", "-").replace("月", "-").replace("日", "");
                            String dateStrB = timeB.replace("年", "-").replace("月", "-").replace("日", "");
                            return dateStrB.compareTo(dateStrA);
                        }
                        // 处理相对时间格式：2天前
                        else if (timeA.contains("天前")) {
                            int daysA = Integer.parseInt(timeA.replaceAll("[^0-9]", ""));
                            int daysB = Integer.parseInt(timeB.replaceAll("[^0-9]", ""));
                            return daysA - daysB;
                        }
                        // 处理其他格式
                        else {
                            return timeB.compareTo(timeA);
                        }
                    } catch (Exception e) {
                        // 排序失败时按原始顺序
                        return 0;
                    }
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getFeedById(@PathVariable int id) {
        return feedList.stream()
                .filter(feed -> (int) feed.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/insert")
    public Map<String, Object> addFeed(@RequestBody Map<String, Object> feedData) {
        Map<String, Object> newFeed = new LinkedHashMap<>(feedData);
        newFeed.put("id", feedList.size() + 1);
        newFeed.put("time", "刚刚");
        newFeed.put("link", feedData.getOrDefault("link", "https://example.com/feed/new"));
        feedList.add(newFeed);
        return newFeed;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateFeed(@PathVariable int id, @RequestBody Map<String, Object> feedData) {
        for (Map<String, Object> feed : feedList) {
            if ((int) feed.get("id") == id) {
                feedData.forEach((key, value) -> {
                    if (!"id".equals(key) && !"time".equals(key)) {
                        feed.put(key, value);
                    }
                });
                return feed;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteFeed(@PathVariable int id) {
        Map<String, Object> result = new LinkedHashMap<>();
        boolean removed = feedList.removeIf(feed -> (int) feed.get("id") == id);
        result.put("success", removed);
        result.put("message", removed ? "删除成功" : "动态不存在");
        return result;
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchFeed(@RequestParam String keyword) {
        return feedList.stream()
                .filter(feed -> {
                    String content = (String) feed.get("content");
                    String username = (String) feed.get("username");
                    return content.contains(keyword) || 
                           username.contains(keyword);
                })
                .collect(java.util.stream.Collectors.toList());
    }
}
