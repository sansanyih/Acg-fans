package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private List<Map<String, Object>> userList = new ArrayList<>();

    public UserController() {
        initUserData();
    }

    private void initUserData() {
        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 1);
        user1.put("username", "咕噜咕噜");
        user1.put("password", "123456");
        user1.put("email", "gulu@example.com");
        user1.put("avatar", "https://example.com/avatar1.jpg");
        user1.put("registerDate", "2025-01-15");
        user1.put("lastLogin", "2026-02-13 18:26:13");
        user1.put("articleCount", 5);
        user1.put("commentCount", 23);
        user1.put("bio", "热爱ACG文化，喜欢分享动漫和游戏的见解，是社区的活跃用户。");
        user1.put("level", 9);
        user1.put("isAdmin", false);
        userList.add(user1);

        Map<String, Object> user2 = new LinkedHashMap<>();
        user2.put("id", 2);
        user2.put("username", "动漫评论家");
        user2.put("password", "password123");
        user2.put("email", "critic@example.com");
        user2.put("avatar", "https://example.com/avatar2.jpg");
        user2.put("registerDate", "2025-03-20");
        user2.put("lastLogin", "2026-03-01 10:00:00");
        user2.put("articleCount", 12);
        user2.put("commentCount", 45);
        user2.put("bio", "资深动漫评论家，专注于新番前瞻和深度解读，拥有丰富的动漫观影经验。");
        user2.put("level", 12);
        user2.put("isAdmin", false);
        userList.add(user2);

        Map<String, Object> user3 = new LinkedHashMap<>();
        user3.put("id", 3);
        user3.put("username", "国漫爱好者");
        user3.put("password", "anime2025");
        user3.put("email", "guoman@example.com");
        user3.put("avatar", "https://example.com/avatar3.jpg");
        user3.put("registerDate", "2025-06-10");
        user3.put("lastLogin", "2026-03-15 15:30:00");
        user3.put("articleCount", 3);
        user3.put("commentCount", 18);
        user3.put("bio", "国漫文化的忠实拥护者，致力于推广和分享优秀的国产动漫作品。");
        user3.put("level", 6);
        user3.put("isAdmin", false);
        userList.add(user3);

        Map<String, Object> user4 = new LinkedHashMap<>();
        user4.put("id", 4);
        user4.put("username", "四大鬼王之首");
        user4.put("password", "ghost2026");
        user4.put("email", "ghostking@example.com");
        user4.put("avatar", "https://example.com/avatar4.jpg");
        user4.put("registerDate", "2025-11-05");
        user4.put("lastLogin", "2026-01-28 00:00:00");
        user4.put("articleCount", 1);
        user4.put("commentCount", 7);
        user4.put("bio", "独立游戏制作人，正在开发galgame《迷城·失落之城》中...");
        user4.put("level", 4);
        user4.put("isAdmin", false);
        userList.add(user4);

        Map<String, Object> user5 = new LinkedHashMap<>();
        user5.put("id", 5);
        user5.put("username", "JOJO粉");
        user5.put("password", "jojo123");
        user5.put("email", "jojo@example.com");
        user5.put("avatar", "https://example.com/avatar5.jpg");
        user5.put("registerDate", "2025-08-22");
        user5.put("lastLogin", "2026-01-01 12:00:00");
        user5.put("articleCount", 0);
        user5.put("commentCount", 15);
        user5.put("bio", "JOJO的奇妙冒险系列的忠实粉丝，最喜欢承太郎的欧拉欧拉！");
        user5.put("level", 5);
        user5.put("isAdmin", false);
        userList.add(user5);

        Map<String, Object> user6 = new LinkedHashMap<>();
        user6.put("id", 6);
        user6.put("username", "动漫迷");
        user6.put("password", "anime456");
        user6.put("email", "animefan@example.com");
        user6.put("avatar", "https://example.com/avatar6.jpg");
        user6.put("registerDate", "2025-09-14");
        user6.put("lastLogin", "2026-01-02 14:30:00");
        user6.put("articleCount", 0);
        user6.put("commentCount", 32);
        user6.put("bio", "动漫爱好者，看过很多经典作品，喜欢在社区和大家讨论剧情。");
        user6.put("level", 7);
        user6.put("isAdmin", false);
        userList.add(user6);

        Map<String, Object> user7 = new LinkedHashMap<>();
        user7.put("id", 7);
        user7.put("username", "(^_^)**");
        user7.put("password", "happy2026");
        user7.put("email", "happy@example.com");
        user7.put("avatar", "https://example.com/comment-avatar1.jpg");
        user7.put("registerDate", "2025-12-01");
        user7.put("lastLogin", "2026-01-31 01:53:00");
        user7.put("articleCount", 0);
        user7.put("commentCount", 1);
        user7.put("bio", "新用户，喜欢帮助他人解决创作问题，对编剧很感兴趣。");
        user7.put("level", 2);
        user7.put("isAdmin", false);
        userList.add(user7);

        // 添加管理员账号
        Map<String, Object> adminUser = new LinkedHashMap<>();
        adminUser.put("id", 8);
        adminUser.put("username", "admin");
        adminUser.put("password", "admin123");
        adminUser.put("email", "admin@example.com");
        adminUser.put("avatar", "https://example.com/admin-avatar.jpg");
        adminUser.put("registerDate", "2025-01-01");
        adminUser.put("lastLogin", "2026-04-14 00:00:00");
        adminUser.put("articleCount", 0);
        adminUser.put("commentCount", 0);
        adminUser.put("bio", "系统管理员账号");
        adminUser.put("level", 20);
        adminUser.put("isAdmin", true);
        userList.add(adminUser);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> loginData) {
        String username = (String) loginData.get("username");
        String password = (String) loginData.get("password");

        Map<String, Object> result = new LinkedHashMap<>();

        for (Map<String, Object> user : userList) {
            if (user.get("username").equals(username) && user.get("password").equals(password)) {
                user.put("lastLogin", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                
                Map<String, Object> userInfo = new LinkedHashMap<>();
                userInfo.put("id", user.get("id"));
                userInfo.put("username", user.get("username"));
                userInfo.put("email", user.get("email"));
                userInfo.put("avatar", user.get("avatar"));
                userInfo.put("registerDate", user.get("registerDate"));
                userInfo.put("lastLogin", user.get("lastLogin"));
                userInfo.put("articleCount", user.get("articleCount"));
                userInfo.put("commentCount", user.get("commentCount"));
                userInfo.put("bio", user.get("bio"));
                userInfo.put("level", user.get("level"));
                userInfo.put("isAdmin", user.get("isAdmin"));

                result.put("success", true);
                result.put("message", "登录成功");
                result.put("user", userInfo);
                return result;
            }
        }

        result.put("success", false);
        result.put("message", "用户名或密码错误");
        return result;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, Object> registerData) {
        String username = (String) registerData.get("username");
        String password = (String) registerData.get("password");
        String email = (String) registerData.get("email");

        Map<String, Object> result = new LinkedHashMap<>();

        for (Map<String, Object> user : userList) {
            if (user.get("username").equals(username)) {
                result.put("success", false);
                result.put("message", "用户名已存在");
                return result;
            }
            if (user.get("email").equals(email)) {
                result.put("success", false);
                result.put("message", "邮箱已被注册");
                return result;
            }
        }

        Map<String, Object> newUser = new LinkedHashMap<>();
        newUser.put("id", userList.size() + 1);
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("email", email);
        newUser.put("avatar", registerData.getOrDefault("avatar", "https://example.com/default-avatar.jpg"));
        newUser.put("registerDate", new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        newUser.put("lastLogin", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        newUser.put("articleCount", 0);
        newUser.put("commentCount", 0);
        newUser.put("bio", registerData.getOrDefault("bio", "这个人很懒，什么都没写~"));
        newUser.put("level", registerData.getOrDefault("level", 1));
        newUser.put("isAdmin", false);
        userList.add(newUser);

        Map<String, Object> userInfo = new LinkedHashMap<>();
        userInfo.put("id", newUser.get("id"));
        userInfo.put("username", newUser.get("username"));
        userInfo.put("email", newUser.get("email"));
        userInfo.put("avatar", newUser.get("avatar"));
        userInfo.put("registerDate", newUser.get("registerDate"));
        userInfo.put("lastLogin", newUser.get("lastLogin"));
        userInfo.put("articleCount", newUser.get("articleCount"));
        userInfo.put("commentCount", newUser.get("commentCount"));
        userInfo.put("bio", newUser.get("bio"));
        userInfo.put("level", newUser.get("level"));
        userInfo.put("isAdmin", newUser.get("isAdmin"));

        result.put("success", true);
        result.put("message", "注册成功");
        result.put("user", userInfo);
        return result;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getUserById(@PathVariable int id) {
        return userList.stream()
                .filter(user -> (int) user.get("id") == id)
                .findFirst()
                .map(user -> {
                    Map<String, Object> userInfo = new LinkedHashMap<>();
                    userInfo.put("id", user.get("id"));
                    userInfo.put("username", user.get("username"));
                    userInfo.put("email", user.get("email"));
                    userInfo.put("avatar", user.get("avatar"));
                    userInfo.put("registerDate", user.get("registerDate"));
                    userInfo.put("lastLogin", user.get("lastLogin"));
                    userInfo.put("articleCount", user.get("articleCount"));
                    userInfo.put("commentCount", user.get("commentCount"));
                    userInfo.put("bio", user.get("bio"));
                    userInfo.put("level", user.get("level"));
                    userInfo.put("isAdmin", user.get("isAdmin"));
                    return userInfo;
                })
                .orElse(null);
    }

    @GetMapping("/get/all")
    public List<Map<String, Object>> getAllUsers() {
        return userList.stream()
                .map(user -> {
                    Map<String, Object> userInfo = new LinkedHashMap<>();
                    userInfo.put("id", user.get("id"));
                    userInfo.put("username", user.get("username"));
                    userInfo.put("email", user.get("email"));
                    userInfo.put("avatar", user.get("avatar"));
                    userInfo.put("registerDate", user.get("registerDate"));
                    userInfo.put("lastLogin", user.get("lastLogin"));
                    userInfo.put("articleCount", user.get("articleCount"));
                    userInfo.put("commentCount", user.get("commentCount"));
                    userInfo.put("bio", user.get("bio"));
                    userInfo.put("level", user.get("level"));
                    userInfo.put("isAdmin", user.get("isAdmin"));
                    return userInfo;
                })
                .collect(java.util.stream.Collectors.toList());
    }
}
