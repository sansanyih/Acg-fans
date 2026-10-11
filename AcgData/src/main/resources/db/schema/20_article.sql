-- ============================================================
-- 文章表（article 模块）
-- 约定：create_time / update_time / deleted 三件套齐全；
--      文章被 LikeService 维护 like_count，故必须有 like_count 列；
--      作者信息（author / author_avatar）冗余成字符串列，保证旧 JSON 形状不变。
-- ============================================================
CREATE TABLE IF NOT EXISTS `article`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`         VARCHAR(255) NOT NULL COMMENT '标题',
    `summary`       VARCHAR(500)          DEFAULT NULL COMMENT '摘要',
    `content`       TEXT         NOT NULL COMMENT '正文（长文本）',
    `cover`         VARCHAR(500)          DEFAULT NULL COMMENT '封面地址',
    `author`        VARCHAR(50)           DEFAULT NULL COMMENT '作者名（冗余，前端字段 author）',
    `author_avatar` VARCHAR(500)          DEFAULT NULL COMMENT '作者头像（冗余，前端字段 authorAvatar）',
    `publish_date`  DATETIME              DEFAULT NULL COMMENT '发布日期（前端字段 publishDate，格式 yyyy-MM-dd）',
    `read_count`    INT          NOT NULL DEFAULT 0 COMMENT '阅读数（前端字段 readCount）',
    `like_count`    INT          NOT NULL DEFAULT 0 COMMENT '点赞数（前端字段 likeCount，由 LikeService 维护）',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间（前端字段 lastEdit 由此格式化而来）',
    `deleted`       TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_article_publish_date` (`publish_date`),
    KEY `idx_article_author` (`author`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章表';
