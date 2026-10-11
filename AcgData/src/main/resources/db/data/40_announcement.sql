-- ============================================================
-- 公告种子数据（把旧 AnnouncementController 里的硬编码假数据原样迁移到表里）
-- 幂等：显式主键 id + ON DUPLICATE KEY UPDATE，可反复执行。
-- create_time / update_time 用旧数据的「发布日期 / lastEdit」回填，
-- 这样前端拿到的 publishDate 与 lastEdit 和旧接口逐字一致。
-- ============================================================
INSERT INTO `announcement` (`id`, `cover`, `title`, `content`, `summary`, `author`, `author_avatar`,
                            `publish_date`, `read_count`, `like_count`, `create_time`, `update_time`)
VALUES (1, 'https://example.com/announcement1.jpg', '网站改版公告', '# 网站改版公告\n\n尊敬的用户：\n\n为了提供更好的用户体验，我们将于2026年4月15日进行网站改版。改版期间网站可能会出现短暂的访问不稳定，请各位用户谅解。\n\n## 改版内容\n- 全新的用户界面设计\n- 优化的响应式布局，支持移动端访问\n- 新增角色详情页功能\n- 改进的搜索功能\n- 优化的视频播放器\n\n## 改版时间\n- 开始时间：2026年4月15日 00:00\n- 预计完成时间：2026年4月15日 06:00\n\n感谢您对我们的支持与理解！\n\nACGData团队\n2026年4月13日', '网站将于2026年4月15日进行改版，新增角色详情页等功能', '系统管理员', 'https://example.com/admin-avatar.jpg', '2026-04-13', 523, 45, '2026-04-13 00:00:00', '2026-04-13 10:00:00'),
       (2, 'https://example.com/announcement2.jpg', 'API接口更新通知', '# API接口更新通知\n\n各位开发者：\n\n我们将于2026年4月10日更新API接口，以下是更新内容：\n\n## 新增接口\n- `/api/anime/get/recent-updated` - 获取最近更新的番剧\n- `/api/character/get` - 获取角色列表\n- `/api/character/get/{id}` - 获取角色详情\n- `/api/character/getByAnime/{animeId}` - 获取番剧的角色列表\n\n## 接口变更\n- `/api/anime/update/{id}` 接口现在会自动更新 `lastEdit` 字段\n- 所有番剧数据现在包含 `viewingRecords` 字段\n\n## 废弃接口\n- 无\n\n请各位开发者及时更新代码，确保与新接口兼容。\n\n如有任何问题，请联系我们的技术支持。\n\nACGData团队\n2026年4月09日', '2026年4月10日API接口更新，新增角色相关接口', '系统管理员', 'https://example.com/admin-avatar.jpg', '2026-04-09', 342, 28, '2026-04-09 00:00:00', '2026-04-09 15:00:00'),
       (3, 'https://example.com/announcement3.jpg', '用户系统上线公告', '# 用户系统上线公告\n\n尊敬的用户：\n\n我们很高兴地宣布，ACGData用户系统现已正式上线！\n\n## 功能介绍\n- 用户注册与登录\n- 个人资料管理\n- 发布评论\n- 点赞功能\n- 个人中心\n\n## 如何使用\n1. 点击右上角的「注册」按钮创建账号\n2. 登录后完善个人资料\n3. 开始使用全部功能\n\n## 注意事项\n- 请保护好您的账号信息\n- 遵守社区规范，文明发言\n- 如有任何问题，请联系我们\n\n感谢您的支持与关注！\n\nACGData团队\n2026年4月05日', 'ACGData用户系统正式上线，支持注册登录、个人资料管理等功能', '系统管理员', 'https://example.com/admin-avatar.jpg', '2026-04-05', 789, 67, '2026-04-05 00:00:00', '2026-04-05 09:00:00'),
       (4, 'https://example.com/announcement4.jpg', '视频功能上线公告', '# 视频功能上线公告\n\n亲爱的用户：\n\n我们很高兴地宣布，ACGData视频功能现已正式上线！\n\n## 功能介绍\n- 视频卡片页浏览\n- 视频详情页查看\n- 视频评论功能\n- 相关番剧推荐\n\n## 首批视频内容\n- 动画AMV作品\n- 官方PV\n- 音乐视频\n- 动漫混剪\n\n我们会持续更新更多优质视频内容，敬请期待！\n\nACGData团队\n2026年3月28日', 'ACGData视频功能正式上线，支持视频浏览、评论和相关番剧推荐', '系统管理员', 'https://example.com/admin-avatar.jpg', '2026-03-28', 456, 39, '2026-03-28 00:00:00', '2026-03-28 14:00:00'),
       (5, 'https://example.com/announcement5.jpg', '文章系统上线公告', '# 文章系统上线公告\n\n尊敬的用户：\n\nACGData文章系统现已正式上线！\n\n## 功能介绍\n- 文章列表浏览\n- 文章详情查看\n- 文章评论功能\n- 文章搜索功能\n\n## 首批文章内容\n- 动漫资讯\n- 番剧推荐\n- 行业动态\n- 创作分享\n\n欢迎各位用户积极参与，分享您的观点和创作！\n\nACGData团队\n2026年3月20日', 'ACGData文章系统正式上线，支持文章浏览、评论和搜索功能', '系统管理员', 'https://example.com/admin-avatar.jpg', '2026-03-20', 321, 25, '2026-03-20 00:00:00', '2026-03-20 11:00:00'),
       (6, 'https://example.com/announcement6.jpg', '网站正式上线公告', '# 网站正式上线公告\n\n亲爱的用户：\n\n经过一段时间的开发和测试，ACGData网站现已正式上线！\n\n## 网站功能\n- 动漫数据浏览\n- 随机动漫推荐\n- 最新番剧查询\n- 即将上映番剧预览\n\n## 后续计划\n- 文章系统\n- 视频系统\n- 用户系统\n- 社区功能\n\n我们将不断完善网站功能，为您提供更好的动漫数据服务。\n\n感谢您的支持与关注！\n\nACGData团队\n2026年3月15日', 'ACGData网站正式上线，提供动漫数据浏览、随机动漫推荐等功能', '系统管理员', 'https://example.com/admin-avatar.jpg', '2026-03-15', 987, 89, '2026-03-15 00:00:00', '2026-03-15 00:00:00')
ON DUPLICATE KEY UPDATE `cover`         = VALUES(`cover`),
                        `title`         = VALUES(`title`),
                        `content`       = VALUES(`content`),
                        `summary`       = VALUES(`summary`),
                        `author`        = VALUES(`author`),
                        `author_avatar` = VALUES(`author_avatar`),
                        `publish_date`  = VALUES(`publish_date`),
                        `read_count`    = VALUES(`read_count`),
                        `like_count`    = VALUES(`like_count`),
                        `create_time`   = VALUES(`create_time`),
                        `update_time`   = VALUES(`update_time`);
