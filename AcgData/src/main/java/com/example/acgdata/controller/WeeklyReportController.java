package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/weekly")
public class WeeklyReportController {

    private List<Map<String, Object>> reportList = new ArrayList<>();

    public WeeklyReportController() {
        initReportData();
    }

    private void initReportData() {
        Map<String, Object> report1 = new LinkedHashMap<>();
        report1.put("id", 1);
        report1.put("cover", "https://example.com/weekly1.jpg");
        report1.put("title", "2026年第13周周报");
        report1.put("content", "# 2026年第13周（2026-04-06 至 2026-04-12）\n\n## 本周概览\n- 完成了用户登录和注册系统的开发\n- 新增了周报功能\n- 优化了文章系统的用户体验\n\n## 详细内容\n\n### 1. 用户系统\n- 实现了登录接口 `/api/user/login`\n- 实现了注册接口 `/api/user/register`\n- 添加了7个示例用户数据\n- 支持个人简介和等级字段\n\n### 2. 文章系统\n- 优化了卡片页接口，添加了封面和用户头像字段\n- 修复了文章内容中的转义字符问题\n- 完善了评论系统\n\n### 3. 下周计划\n- 实现周报的评论功能\n- 添加周报的点赞和阅读量统计\n- 优化前端页面的响应式设计\n\n## 技术栈\n- Spring Boot 3.0+\n- Java 17\n- RESTful API\n\n## 总结\n本周完成了用户系统的核心功能，为后续的功能扩展打下了基础。下周将继续完善周报系统和前端页面。");
        report1.put("author", "系统管理员");
        report1.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        report1.put("publishDate", "2026-04-12");
        report1.put("readCount", 156);
        report1.put("likeCount", 8);
        report1.put("summary", "2026年第13周工作周报，包含用户系统和文章系统的开发进展");
        report1.put("lastEdit", "2026年4月12日 18:00:00");
        
        List<Map<String, Object>> comments1 = new ArrayList<>();
        Map<String, Object> comment1 = new LinkedHashMap<>();
        comment1.put("id", 1);
        comment1.put("user", "咕噜咕噜");
        comment1.put("userAvatar", "https://example.com/avatar1.jpg");
        comment1.put("content", "周报内容很详细，期待下周的进展！");
        comment1.put("date", "2026-04-12 19:30:00");
        comment1.put("replyCount", 0);
        comments1.add(comment1);
        report1.put("comments", comments1);
        reportList.add(report1);

        Map<String, Object> report2 = new LinkedHashMap<>();
        report2.put("id", 2);
        report2.put("cover", "https://example.com/weekly2.jpg");
        report2.put("title", "2026年第12周周报");
        report2.put("content", "# 2026年第12周（2026-03-30 至 2026-04-05）\n\n## 本周概览\n- 完成了文章系统的开发\n- 实现了文章的CRUD操作\n- 添加了评论系统\n\n## 详细内容\n\n### 1. 文章系统\n- 实现了文章列表接口 `/api/article/get`\n- 实现了文章详情接口 `/api/article/get/{id}`\n- 实现了文章添加接口 `/api/article/insert`\n- 实现了文章更新接口 `/api/article/update/{id}`\n- 实现了文章删除接口 `/api/article/delete/{id}`\n\n### 2. 评论系统\n- 实现了评论添加接口 `/api/article/addComment/{id}`\n- 支持评论的回复功能\n- 添加了评论的用户信息显示\n\n### 3. 下周计划\n- 开发用户登录和注册系统\n- 实现周报功能\n- 优化前端页面\n\n## 技术栈\n- Spring Boot 3.0+\n- Java 17\n- RESTful API\n\n## 总结\n本周完成了文章系统的核心功能，为社区提供了内容发布和交流的平台。下周将开发用户系统和周报功能。");
        report2.put("author", "系统管理员");
        report2.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        report2.put("publishDate", "2026-04-05");
        report2.put("readCount", 123);
        report2.put("likeCount", 5);
        report2.put("summary", "2026年第12周工作周报，包含文章系统和评论系统的开发进展");
        report2.put("lastEdit", "2026年4月5日 17:30:00");
        report2.put("comments", new ArrayList<Map<String, Object>>());
        reportList.add(report2);

        Map<String, Object> report3 = new LinkedHashMap<>();
        report3.put("id", 3);
        report3.put("cover", "https://example.com/weekly3.jpg");
        report3.put("title", "2026年第11周周报");
        report3.put("content", "# 2026年第11周（2026-03-23 至 2026-03-29）\n\n## 本周概览\n- 完成了动漫数据接口的开发\n- 实现了随机动漫和最新动漫接口\n- 添加了即将上映的动漫数据\n\n## 详细内容\n\n### 1. 动漫系统\n- 实现了随机动漫接口 `/api/anime/get/random`\n- 实现了最新动漫接口 `/api/anime/get/latest`\n- 实现了即将上映动漫接口 `/api/anime/get/upcoming`\n- 添加了多部动漫数据，包括《刀剑神域》等\n\n### 2. 技术优化\n- 修复了日期排序中的null值问题\n- 优化了数据结构和响应格式\n- 提高了接口的稳定性\n\n### 3. 下周计划\n- 开发文章系统\n- 实现评论功能\n- 完善前端页面\n\n## 技术栈\n- Spring Boot 3.0+\n- Java 17\n- RESTful API\n\n## 总结\n本周完成了动漫数据接口的开发，为用户提供了丰富的动漫信息。下周将开发文章系统和评论功能。");
        report3.put("author", "系统管理员");
        report3.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        report3.put("publishDate", "2026-03-29");
        report3.put("readCount", 98);
        report3.put("likeCount", 3);
        report3.put("summary", "2026年第11周工作周报，包含动漫数据接口的开发进展");
        report3.put("lastEdit", "2026年3月29日 16:45:00");
        report3.put("comments", new ArrayList<Map<String, Object>>());
        reportList.add(report3);

        Map<String, Object> report4 = new LinkedHashMap<>();
        report4.put("id", 4);
        report4.put("cover", "https://example.com/weekly4.jpg");
        report4.put("title", "2026年第10周周报");
        report4.put("content", "# 2026年第10周（2026-03-16 至 2026-03-22）\n\n## 本周概览\n- 项目初始化\n- 搭建了基础架构\n- 实现了基础的RESTful API\n\n## 详细内容\n\n### 1. 项目初始化\n- 创建了Spring Boot项目\n- 配置了基础依赖\n- 搭建了项目结构\n\n### 2. 基础架构\n- 实现了控制器层\n- 配置了路由\n- 测试了基础接口\n\n### 3. 下周计划\n- 开发动漫数据接口\n- 实现数据模型\n- 完善API文档\n\n## 技术栈\n- Spring Boot 3.0+\n- Java 17\n- RESTful API\n\n## 总结\n本周完成了项目的初始化和基础架构的搭建，为后续的功能开发做好了准备。下周将开始开发动漫数据接口。");
        report4.put("author", "系统管理员");
        report4.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        report4.put("publishDate", "2026-03-22");
        report4.put("readCount", 76);
        report4.put("likeCount", 2);
        report4.put("summary", "2026年第10周工作周报，包含项目初始化和基础架构搭建");
        report4.put("lastEdit", "2026年3月22日 15:20:00");
        report4.put("comments", new ArrayList<Map<String, Object>>());
        reportList.add(report4);

        Map<String, Object> report5 = new LinkedHashMap<>();
        report5.put("id", 5);
        report5.put("cover", "https://example.com/weekly5.jpg");
        report5.put("title", "2026年第9周周报");
        report5.put("content", "# 2026年第9周（2026-03-09 至 2026-03-15）\n\n## 本周概览\n- 完成了项目需求分析\n- 制定了开发计划\n- 准备了开发环境\n\n## 详细内容\n\n### 1. 需求分析\n- 分析了用户需求\n- 确定了核心功能\n- 制定了技术方案\n\n### 2. 开发计划\n- 制定了详细的开发计划\n- 分配了开发任务\n- 确定了里程碑\n\n### 3. 下周计划\n- 开始项目初始化\n- 搭建基础架构\n- 实现基础API\n\n## 技术栈\n- Spring Boot 3.0+\n- Java 17\n- RESTful API\n\n## 总结\n本周完成了项目的需求分析和开发计划制定，为后续的开发工作做好了准备。下周将开始项目初始化和基础架构搭建。");
        report5.put("author", "系统管理员");
        report5.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        report5.put("publishDate", "2026-03-15");
        report5.put("readCount", 54);
        report5.put("likeCount", 1);
        report5.put("summary", "2026年第9周工作周报，包含需求分析和开发计划制定");
        report5.put("lastEdit", "2026年3月15日 14:15:00");
        report5.put("comments", new ArrayList<Map<String, Object>>());
        reportList.add(report5);

        Map<String, Object> report6 = new LinkedHashMap<>();
        report6.put("id", 6);
        report6.put("cover", "https://example.com/weekly6.jpg");
        report6.put("title", "2026年第8周周报");
        report6.put("content", "# 2026年第8周（2026-03-02 至 2026-03-08）\n\n## 本周概览\n- 项目启动\n- 组建了开发团队\n- 进行了技术选型\n\n## 详细内容\n\n### 1. 项目启动\n- 召开了项目启动会议\n- 明确了项目目标\n- 确定了项目范围\n\n### 2. 团队组建\n- 组建了开发团队\n- 分配了角色和职责\n- 建立了沟通机制\n\n### 3. 技术选型\n- 选择了技术栈\n- 评估了技术方案\n- 确定了开发工具\n\n### 4. 下周计划\n- 进行需求分析\n- 制定开发计划\n- 准备开发环境\n\n## 技术栈\n- Spring Boot 3.0+\n- Java 17\n- RESTful API\n\n## 总结\n本周完成了项目的启动和团队组建，为后续的开发工作奠定了基础。下周将进行需求分析和开发计划制定。");
        report6.put("author", "系统管理员");
        report6.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        report6.put("publishDate", "2026-03-08");
        report6.put("readCount", 32);
        report6.put("likeCount", 0);
        report6.put("summary", "2026年第8周工作周报，包含项目启动和团队组建");
        report6.put("lastEdit", "2026年3月8日 13:45:00");
        report6.put("comments", new ArrayList<Map<String, Object>>());
        reportList.add(report6);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getReportList() {
        return reportList.stream()
                .sorted((a, b) -> {
                    String dateA = (String) a.get("publishDate");
                    String dateB = (String) b.get("publishDate");
                    return dateB.compareTo(dateA);
                })
                .limit(5)
                .map(report -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", report.get("id"));
                    listItem.put("cover", report.get("cover"));
                    listItem.put("title", report.get("title"));
                    listItem.put("summary", report.get("summary"));
                    listItem.put("author", report.get("author"));
                    listItem.put("authorAvatar", report.get("authorAvatar"));
                    listItem.put("publishDate", report.get("publishDate"));
                    listItem.put("readCount", report.get("readCount"));
                    listItem.put("likeCount", report.get("likeCount"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getReportById(@PathVariable int id) {
        return reportList.stream()
                .filter(report -> (int) report.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/insert")
    public Map<String, Object> addReport(@RequestBody Map<String, Object> reportData) {
        Map<String, Object> newReport = new LinkedHashMap<>(reportData);
        newReport.put("id", reportList.size() + 1);
        newReport.put("readCount", 0);
        newReport.put("likeCount", 0);
        newReport.put("publishDate", new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        newReport.put("lastEdit", new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        newReport.put("cover", reportData.getOrDefault("cover", "https://example.com/default-cover.jpg"));
        newReport.put("authorAvatar", reportData.getOrDefault("authorAvatar", "https://example.com/admin-avatar.jpg"));
        newReport.put("comments", new ArrayList<Map<String, Object>>());
        reportList.add(newReport);
        return newReport;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateReport(@PathVariable int id, @RequestBody Map<String, Object> reportData) {
        for (Map<String, Object> report : reportList) {
            if ((int) report.get("id") == id) {
                reportData.forEach((key, value) -> {
                    if (!"id".equals(key) && !"readCount".equals(key) && !"likeCount".equals(key) && !"publishDate".equals(key)) {
                        report.put(key, value);
                    }
                });
                report.put("lastEdit", new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                return report;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteReport(@PathVariable int id) {
        Map<String, Object> result = new LinkedHashMap<>();
        boolean removed = reportList.removeIf(report -> (int) report.get("id") == id);
        result.put("success", removed);
        result.put("message", removed ? "删除成功" : "周报不存在");
        return result;
    }

    @PostMapping("/addComment/{id}")
    public Map<String, Object> addComment(@PathVariable int id, @RequestBody Map<String, Object> commentData) {
        for (Map<String, Object> report : reportList) {
            if ((int) report.get("id") == id) {
                List<Map<String, Object>> comments = (List<Map<String, Object>>) report.get("comments");
                Map<String, Object> newComment = new LinkedHashMap<>();
                newComment.put("id", comments.size() + 1);
                newComment.put("user", commentData.get("user"));
                newComment.put("userAvatar", commentData.getOrDefault("userAvatar", "https://example.com/default-avatar.jpg"));
                newComment.put("content", commentData.get("content"));
                newComment.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                newComment.put("replyCount", 0);
                comments.add(newComment);
                return newComment;
            }
        }
        return null;
    }

    @PostMapping("/like/{id}")
    public Map<String, Object> likeReport(@PathVariable int id) {
        for (Map<String, Object> report : reportList) {
            if ((int) report.get("id") == id) {
                int likeCount = (int) report.get("likeCount");
                report.put("likeCount", likeCount + 1);
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("success", true);
                result.put("likeCount", report.get("likeCount"));
                return result;
            }
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", false);
        result.put("message", "周报不存在");
        return result;
    }
}
