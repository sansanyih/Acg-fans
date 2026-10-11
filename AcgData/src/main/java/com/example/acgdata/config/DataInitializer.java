package com.example.acgdata.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.acgdata.entity.User;
import com.example.acgdata.service.EditStatisticService;
import com.example.acgdata.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 启动初始化：演示账号（密码统一 BCrypt 加密后入库）与编辑统计演示数据。
 * 幂等：已存在则跳过，不会覆盖用户自己注册的数据。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserService userService;
    private final EditStatisticService editStatisticService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(ApplicationArguments args) {
        initUsers();
        editStatisticService.initIfEmpty(30);
    }

    private void initUsers() {
        createIfAbsent("admin", "admin123", "admin@example.com", true,
                "系统管理员账号", 20, 0, 0, LocalDate.of(2025, 1, 1),
                "https://example.com/admin-avatar.jpg");
        createIfAbsent("咕噜咕噜", "123456", "gulu@example.com", false,
                "热爱ACG文化，喜欢分享动漫和游戏的见解，是社区的活跃用户。", 9, 5, 23,
                LocalDate.of(2025, 1, 15), "https://example.com/avatar1.jpg");
        createIfAbsent("动漫评论家", "password123", "critic@example.com", false,
                "资深动漫评论家，专注于新番前瞻和深度解读，拥有丰富的动漫观影经验。", 12, 12, 45,
                LocalDate.of(2025, 3, 20), "https://example.com/avatar2.jpg");
        createIfAbsent("国漫爱好者", "anime2025", "guoman@example.com", false,
                "国漫文化的忠实拥护者，致力于推广和分享优秀的国产动漫作品。", 6, 3, 18,
                LocalDate.of(2025, 6, 10), "https://example.com/avatar3.jpg");
        createIfAbsent("四大鬼王之首", "ghost2026", "ghostking@example.com", false,
                "独立游戏制作人，正在开发galgame《迷城·失落之城》中...", 4, 1, 7,
                LocalDate.of(2025, 11, 5), "https://example.com/avatar4.jpg");
        createIfAbsent("JOJO粉", "jojo123", "jojo@example.com", false,
                "JOJO的奇妙冒险系列的忠实粉丝，最喜欢承太郎的欧拉欧拉！", 5, 0, 15,
                LocalDate.of(2025, 8, 22), "https://example.com/avatar5.jpg");
        createIfAbsent("动漫迷", "anime456", "animefan@example.com", false,
                "动漫爱好者，看过很多经典作品，喜欢在社区和大家讨论剧情。", 7, 0, 32,
                LocalDate.of(2025, 9, 14), "https://example.com/avatar6.jpg");
        createIfAbsent("(^_^)**", "happy2026", "happy@example.com", false,
                "新用户，喜欢帮助他人解决创作问题，对编剧很感兴趣。", 2, 0, 1,
                LocalDate.of(2025, 12, 1), "https://example.com/comment-avatar1.jpg");

        log.info("演示账号就绪：管理员 admin / admin123，普通用户 咕噜咕噜 / 123456");
    }

    private void createIfAbsent(String username, String rawPassword, String email, boolean admin,
                                String bio, int level, int articleCount, int commentCount,
                                LocalDate registerDate, String avatar) {
        User exists = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username), false);
        if (exists != null) {
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEmail(email);
        user.setAvatar(avatar);
        user.setRegisterDate(registerDate);
        user.setLastLogin(java.time.LocalDateTime.now());
        user.setArticleCount(articleCount);
        user.setCommentCount(commentCount);
        user.setBio(bio);
        user.setLevel(level);
        user.setIsAdmin(admin);
        userService.save(user);
    }
}
