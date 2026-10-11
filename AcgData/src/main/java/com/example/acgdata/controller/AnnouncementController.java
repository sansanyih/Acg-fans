package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    private List<Map<String, Object>> announcementList = new ArrayList<>();

    public AnnouncementController() {
        initAnnouncementData();
    }

    private void initAnnouncementData() {
        Map<String, Object> announcement1 = new LinkedHashMap<>();
        announcement1.put("id", 1);
        announcement1.put("cover", "https://example.com/announcement1.jpg");
        announcement1.put("title", "网站改版公告");
        announcement1.put("content", "# 网站改版公告\n\n尊敬的用户：\n\n为了提供更好的用户体验，我们将于2026年4月15日进行网站改版。改版期间网站可能会出现短暂的访问不稳定，请各位用户谅解。\n\n## 改版内容\n- 全新的用户界面设计\n- 优化的响应式布局，支持移动端访问\n- 新增角色详情页功能\n- 改进的搜索功能\n- 优化的视频播放器\n\n## 改版时间\n- 开始时间：2026年4月15日 00:00\n- 预计完成时间：2026年4月15日 06:00\n\n感谢您对我们的支持与理解！\n\nACGData团队\n2026年4月13日");
        announcement1.put("author", "系统管理员");
        announcement1.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        announcement1.put("publishDate", "2026-04-13");
        announcement1.put("readCount", 523);
        announcement1.put("likeCount", 45);
        announcement1.put("summary", "网站将于2026年4月15日进行改版，新增角色详情页等功能");
        announcement1.put("lastEdit", "2026年4月13日 10:00:00");
        
        List<Map<String, Object>> comments1 = new ArrayList<>();
        Map<String, Object> comment1 = new LinkedHashMap<>();
        comment1.put("id", 1);
        comment1.put("user", "咕噜咕噜");
        comment1.put("userAvatar", "https://example.com/avatar1.jpg");
        comment1.put("content", "期待新版本！");
        comment1.put("date", "2026-04-13 10:30:00");
        comment1.put("replyCount", 0);
        comments1.add(comment1);
        announcement1.put("comments", comments1);
        announcementList.add(announcement1);

        Map<String, Object> announcement2 = new LinkedHashMap<>();
        announcement2.put("id", 2);
        announcement2.put("cover", "https://example.com/announcement2.jpg");
        announcement2.put("title", "API接口更新通知");
        announcement2.put("content", "# API接口更新通知\n\n各位开发者：\n\n我们将于2026年4月10日更新API接口，以下是更新内容：\n\n## 新增接口\n- `/api/anime/get/recent-updated` - 获取最近更新的番剧\n- `/api/character/get` - 获取角色列表\n- `/api/character/get/{id}` - 获取角色详情\n- `/api/character/getByAnime/{animeId}` - 获取番剧的角色列表\n\n## 接口变更\n- `/api/anime/update/{id}` 接口现在会自动更新 `lastEdit` 字段\n- 所有番剧数据现在包含 `viewingRecords` 字段\n\n## 废弃接口\n- 无\n\n请各位开发者及时更新代码，确保与新接口兼容。\n\n如有任何问题，请联系我们的技术支持。\n\nACGData团队\n2026年4月09日");
        announcement2.put("author", "系统管理员");
        announcement2.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        announcement2.put("publishDate", "2026-04-09");
        announcement2.put("readCount", 342);
        announcement2.put("likeCount", 28);
        announcement2.put("summary", "2026年4月10日API接口更新，新增角色相关接口");
        announcement2.put("lastEdit", "2026年4月09日 15:00:00");
        announcement2.put("comments", new ArrayList<Map<String, Object>>());
        announcementList.add(announcement2);

        Map<String, Object> announcement3 = new LinkedHashMap<>();
        announcement3.put("id", 3);
        announcement3.put("cover", "https://example.com/announcement3.jpg");
        announcement3.put("title", "用户系统上线公告");
        announcement3.put("content", "# 用户系统上线公告\n\n尊敬的用户：\n\n我们很高兴地宣布，ACGData用户系统现已正式上线！\n\n## 功能介绍\n- 用户注册与登录\n- 个人资料管理\n- 发布评论\n- 点赞功能\n- 个人中心\n\n## 如何使用\n1. 点击右上角的「注册」按钮创建账号\n2. 登录后完善个人资料\n3. 开始使用全部功能\n\n## 注意事项\n- 请保护好您的账号信息\n- 遵守社区规范，文明发言\n- 如有任何问题，请联系我们\n\n感谢您的支持与关注！\n\nACGData团队\n2026年4月05日");
        announcement3.put("author", "系统管理员");
        announcement3.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        announcement3.put("publishDate", "2026-04-05");
        announcement3.put("readCount", 789);
        announcement3.put("likeCount", 67);
        announcement3.put("summary", "ACGData用户系统正式上线，支持注册登录、个人资料管理等功能");
        announcement3.put("lastEdit", "2026年4月05日 09:00:00");
        announcement3.put("comments", new ArrayList<Map<String, Object>>());
        announcementList.add(announcement3);

        Map<String, Object> announcement4 = new LinkedHashMap<>();
        announcement4.put("id", 4);
        announcement4.put("cover", "https://example.com/announcement4.jpg");
        announcement4.put("title", "视频功能上线公告");
        announcement4.put("content", "# 视频功能上线公告\n\n亲爱的用户：\n\n我们很高兴地宣布，ACGData视频功能现已正式上线！\n\n## 功能介绍\n- 视频卡片页浏览\n- 视频详情页查看\n- 视频评论功能\n- 相关番剧推荐\n\n## 首批视频内容\n- 动画AMV作品\n- 官方PV\n- 音乐视频\n- 动漫混剪\n\n我们会持续更新更多优质视频内容，敬请期待！\n\nACGData团队\n2026年3月28日");
        announcement4.put("author", "系统管理员");
        announcement4.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        announcement4.put("publishDate", "2026-03-28");
        announcement4.put("readCount", 456);
        announcement4.put("likeCount", 39);
        announcement4.put("summary", "ACGData视频功能正式上线，支持视频浏览、评论和相关番剧推荐");
        announcement4.put("lastEdit", "2026年3月28日 14:00:00");
        announcement4.put("comments", new ArrayList<Map<String, Object>>());
        announcementList.add(announcement4);

        Map<String, Object> announcement5 = new LinkedHashMap<>();
        announcement5.put("id", 5);
        announcement5.put("cover", "https://example.com/announcement5.jpg");
        announcement5.put("title", "文章系统上线公告");
        announcement5.put("content", "# 文章系统上线公告\n\n尊敬的用户：\n\nACGData文章系统现已正式上线！\n\n## 功能介绍\n- 文章列表浏览\n- 文章详情查看\n- 文章评论功能\n- 文章搜索功能\n\n## 首批文章内容\n- 动漫资讯\n- 番剧推荐\n- 行业动态\n- 创作分享\n\n欢迎各位用户积极参与，分享您的观点和创作！\n\nACGData团队\n2026年3月20日");
        announcement5.put("author", "系统管理员");
        announcement5.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        announcement5.put("publishDate", "2026-03-20");
        announcement5.put("readCount", 321);
        announcement5.put("likeCount", 25);
        announcement5.put("summary", "ACGData文章系统正式上线，支持文章浏览、评论和搜索功能");
        announcement5.put("lastEdit", "2026年3月20日 11:00:00");
        announcement5.put("comments", new ArrayList<Map<String, Object>>());
        announcementList.add(announcement5);

        Map<String, Object> announcement6 = new LinkedHashMap<>();
        announcement6.put("id", 6);
        announcement6.put("cover", "https://example.com/announcement6.jpg");
        announcement6.put("title", "网站正式上线公告");
        announcement6.put("content", "# 网站正式上线公告\n\n亲爱的用户：\n\n经过一段时间的开发和测试，ACGData网站现已正式上线！\n\n## 网站功能\n- 动漫数据浏览\n- 随机动漫推荐\n- 最新番剧查询\n- 即将上映番剧预览\n\n## 后续计划\n- 文章系统\n- 视频系统\n- 用户系统\n- 社区功能\n\n我们将不断完善网站功能，为您提供更好的动漫数据服务。\n\n感谢您的支持与关注！\n\nACGData团队\n2026年3月15日");
        announcement6.put("author", "系统管理员");
        announcement6.put("authorAvatar", "https://example.com/admin-avatar.jpg");
        announcement6.put("publishDate", "2026-03-15");
        announcement6.put("readCount", 987);
        announcement6.put("likeCount", 89);
        announcement6.put("summary", "ACGData网站正式上线，提供动漫数据浏览、随机动漫推荐等功能");
        announcement6.put("lastEdit", "2026年3月15日 00:00:00");
        announcement6.put("comments", new ArrayList<Map<String, Object>>());
        announcementList.add(announcement6);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getAnnouncementList() {
        return announcementList.stream()
                .sorted((a, b) -> {
                    String dateA = (String) a.get("publishDate");
                    String dateB = (String) b.get("publishDate");
                    return dateB.compareTo(dateA);
                })
                .map(announcement -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", announcement.get("id"));
                    listItem.put("cover", announcement.get("cover"));
                    listItem.put("title", announcement.get("title"));
                    listItem.put("summary", announcement.get("summary"));
                    listItem.put("author", announcement.get("author"));
                    listItem.put("authorAvatar", announcement.get("authorAvatar"));
                    listItem.put("publishDate", announcement.get("publishDate"));
                    listItem.put("readCount", announcement.get("readCount"));
                    listItem.put("likeCount", announcement.get("likeCount"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getAnnouncementById(@PathVariable int id) {
        return announcementList.stream()
                .filter(announcement -> (int) announcement.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/insert")
    public Map<String, Object> addAnnouncement(@RequestBody Map<String, Object> announcementData) {
        Map<String, Object> newAnnouncement = new LinkedHashMap<>(announcementData);
        newAnnouncement.put("id", announcementList.size() + 1);
        newAnnouncement.put("readCount", 0);
        newAnnouncement.put("likeCount", 0);
        newAnnouncement.put("publishDate", new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        newAnnouncement.put("lastEdit", new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        newAnnouncement.put("cover", announcementData.getOrDefault("cover", "https://example.com/default-cover.jpg"));
        newAnnouncement.put("authorAvatar", announcementData.getOrDefault("authorAvatar", "https://example.com/admin-avatar.jpg"));
        newAnnouncement.put("comments", new ArrayList<Map<String, Object>>());
        announcementList.add(newAnnouncement);
        return newAnnouncement;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateAnnouncement(@PathVariable int id, @RequestBody Map<String, Object> announcementData) {
        for (Map<String, Object> announcement : announcementList) {
            if ((int) announcement.get("id") == id) {
                announcementData.forEach((key, value) -> {
                    if (!"id".equals(key) && !"readCount".equals(key) && !"likeCount".equals(key) && !"publishDate".equals(key)) {
                        announcement.put(key, value);
                    }
                });
                announcement.put("lastEdit", new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                return announcement;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteAnnouncement(@PathVariable int id) {
        Map<String, Object> result = new LinkedHashMap<>();
        boolean removed = announcementList.removeIf(announcement -> (int) announcement.get("id") == id);
        result.put("success", removed);
        result.put("message", removed ? "删除成功" : "公告不存在");
        return result;
    }

    @PostMapping("/addComment/{id}")
    public Map<String, Object> addComment(@PathVariable int id, @RequestBody Map<String, Object> commentData) {
        for (Map<String, Object> announcement : announcementList) {
            if ((int) announcement.get("id") == id) {
                List<Map<String, Object>> comments = (List<Map<String, Object>>) announcement.get("comments");
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
    public Map<String, Object> likeAnnouncement(@PathVariable int id) {
        for (Map<String, Object> announcement : announcementList) {
            if ((int) announcement.get("id") == id) {
                int likeCount = (int) announcement.get("likeCount");
                announcement.put("likeCount", likeCount + 1);
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("success", true);
                result.put("likeCount", announcement.get("likeCount"));
                return result;
            }
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", false);
        result.put("message", "公告不存在");
        return result;
    }
}