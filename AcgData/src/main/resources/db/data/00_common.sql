-- ============================================================
-- 公共初始化数据：标签字典
-- 幂等写法：INSERT ... ON DUPLICATE KEY UPDATE，可重复启动
-- ============================================================

INSERT INTO `tag` (`name`, `type`)
VALUES ('战斗', 'anime'),
       ('热血', 'anime'),
       ('超能力', 'anime'),
       ('冒险', 'anime'),
       ('奇幻', 'anime'),
       ('科幻', 'anime'),
       ('机战', 'anime'),
       ('日常', 'anime'),
       ('搞笑', 'anime'),
       ('恋爱', 'anime'),
       ('校园', 'anime'),
       ('治愈', 'anime'),
       ('悬疑', 'anime'),
       ('推理', 'anime'),
       ('恐怖', 'anime'),
       ('运动', 'anime'),
       ('音乐', 'anime'),
       ('历史', 'anime'),
       ('美食', 'anime'),
       ('后宫', 'anime'),
       ('原创', 'anime'),
       ('漫画改', 'anime'),
       ('轻小说改', 'anime'),
       ('游戏改', 'anime')
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
