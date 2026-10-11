-- ============================================================
-- 动态种子数据（迁移自旧 FeedController 的内存假数据）
-- 幂等：显式主键 id + ON DUPLICATE KEY UPDATE，可重复执行
-- 说明：publish_time 决定列表排序（倒序），time_label 是逐字返回给前端的展示文案
-- ============================================================
INSERT INTO `feed` (`id`, `user_avatar`, `username`, `publish_time`, `time_label`, `content`, `link`, `like_count`)
VALUES (1, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/Feed-Aniplex.jpg', 'Aniplex_Official',
        '2026-04-10 10:00:00', '2026年04月10日', '分享图片你的人格诊断是？',
        'https://www.bilibili.com/opus/1189617666584739889?spm_id_from=333.1387.0.0', 0),
       (4, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/Feed-fanshi.jpg', '泛式',
        '2026-04-06 10:00:00', '2026年04月6日', '泛式队长，完结吐槽！',
        'https://t.bilibili.com/1188147232997638194?spm_id_from=333.1387.0.0', 0),
       (2, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-smbb.jpg', '数码宝贝官方',
        '2026-04-02 10:00:00', '2026年04月02日', '自2006年4月2日播出的动画『数码宝贝 拯救队』今天迎来了第20周年的纪念日',
        'https://www.bilibili.com/opus/1186544847255765013?spm_id_from=333.1387.0.0', 0),
       (3, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-toei.jpg', '东映动画',
        '2026-04-09 10:00:00', '2026年04月09日', '今天是《境界触发者》中 迅悠一的生日哦！',
        'https://www.bilibili.com/opus/1189111519670960153?spm_id_from=333.1387.0.0', 0),
       (5, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-gbc.png', '少女乐队的呐喊官方',
        '2026-04-07 10:00:00', '2026年04月07日', '【特别新品❗「毛绒指偶公仔」实拍首发',
        'https://t.bilibili.com/1188494863327297545?spm_id_from=333.1387.0.0', 0),
       (6, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-xrht.jpg', '夏日幻听MCE',
        '2026-04-12 10:00:00', '2天前', '4月新番《#关于邻家的天使大人不知不觉把我惯成了废人这档子事#》第二季 02 已更新！',
        'https://t.bilibili.com/1189717782239903745?spm_id_from=333.1387.0.0', 0),
       (7, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/Feed-Aniplex.jpg', 'Aniplex_Official',
        '2026-04-12 09:00:00', '2天前', '【海外情报】《主播女孩重度依赖》角色KV 超绝最可爱天使酱 ',
        'https://www.bilibili.com/opus/1189235214729084933?spm_id_from=333.1387.0.0', 0),
       (8, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/feed-152.jpg', '瓶子君152',
        '2026-03-28 10:00:00', '2026年03月28日', '没有人会记得按时发布的垃圾视频',
        'https://t.bilibili.com/1184785343053824005?spm_id_from=333.1387.0.0', 0),
       (9, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/Feed-Aniplex.jpg', 'Aniplex_Official',
        '2026-03-31 10:00:00', '2026年03月31日', '魔法少女小圆 × 东京晴空塔「月夜华尔兹」',
        'https://www.bilibili.com/opus/1185779741794238484?spm_id_from=333.1387.0.0', 0)
ON DUPLICATE KEY UPDATE `user_avatar`  = VALUES(`user_avatar`),
                        `username`     = VALUES(`username`),
                        `publish_time` = VALUES(`publish_time`),
                        `time_label`   = VALUES(`time_label`),
                        `content`      = VALUES(`content`),
                        `link`         = VALUES(`link`),
                        `like_count`   = VALUES(`like_count`);
