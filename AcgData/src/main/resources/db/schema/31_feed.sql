-- ============================================================
-- 动态表（feed 模块）
-- 说明：publish_time 用于排序（等价于旧 Controller 的时间排序规则），
--      time_label 是直接返回给前端的展示文案（如「2026年04月10日」「2天前」「刚刚」），
--      两者分开存，保证展示与旧接口逐字一致。
-- ============================================================
CREATE TABLE IF NOT EXISTS `feed`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_avatar`  VARCHAR(500)          DEFAULT NULL COMMENT '发布者头像',
    `username`     VARCHAR(100) NOT NULL COMMENT '发布者名称',
    `publish_time` DATETIME     NOT NULL COMMENT '发布时间（排序用）',
    `time_label`   VARCHAR(50)  NOT NULL COMMENT '展示时间文案（前端字段名 time）',
    `content`      TEXT         NOT NULL COMMENT '动态内容',
    `link`         VARCHAR(500)          DEFAULT NULL COMMENT '跳转链接',
    `like_count`   INT          NOT NULL DEFAULT 0 COMMENT '点赞数（全局约定列）',
    `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_feed_publish_time` (`publish_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='动态表';
