-- ============================================================
-- 公告表（announcement 模块）
-- 约定：统一带 create_time / update_time / deleted（逻辑删除）。
--      不单独存 lastEdit：出参的 lastEdit 就是本表的 update_time（DATE_FORMAT 格式化）。
--      like_count 供共享 LikeService（targetType = announcement）原子维护。
-- ============================================================
CREATE TABLE IF NOT EXISTS `announcement`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `cover`         VARCHAR(500)          DEFAULT NULL COMMENT '封面图地址',
    `title`         VARCHAR(200) NOT NULL COMMENT '标题',
    `content`       TEXT                  DEFAULT NULL COMMENT '正文（Markdown）',
    `summary`       VARCHAR(500)          DEFAULT NULL COMMENT '摘要',
    `author`        VARCHAR(50)  NOT NULL DEFAULT '系统管理员' COMMENT '作者',
    `author_avatar` VARCHAR(500)          DEFAULT NULL COMMENT '作者头像地址',
    `publish_date`  DATE         NOT NULL COMMENT '发布日期（前端字段 publishDate，格式 yyyy-MM-dd）',
    `read_count`    INT          NOT NULL DEFAULT 0 COMMENT '阅读量',
    `like_count`    INT          NOT NULL DEFAULT 0 COMMENT '点赞数（由 LikeService 维护，业务不要自己加减）',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间（出参里的 lastEdit）',
    `deleted`       TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_announcement_publish_date` (`publish_date`, `id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='网站公告表';
