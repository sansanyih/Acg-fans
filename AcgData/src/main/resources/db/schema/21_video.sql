-- ============================================================
-- 视频表（video 模块）
-- 约定：create_time / update_time / deleted 三件套齐全；
--      视频被 LikeService 维护 like_count，故必须有 like_count 列；
--      作者信息、关联标签、关联番剧都以冗余形式存在本表，
--      以避免对尚未落库的 anime 模块产生编译期/运行期依赖。
-- ============================================================
CREATE TABLE IF NOT EXISTS `video`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`          VARCHAR(255) NOT NULL COMMENT '标题',
    `thumbnail`      VARCHAR(500)          DEFAULT NULL COMMENT '封面图（前端字段 thumbnail）',
    `author`         VARCHAR(50)           DEFAULT NULL COMMENT '作者名（冗余）',
    `author_avatar`  VARCHAR(500)          DEFAULT NULL COMMENT '作者头像（冗余）',
    `author_role`    VARCHAR(50)           DEFAULT NULL COMMENT '作者身份（前端字段 authorRole）',
    `author_level`   INT          NOT NULL DEFAULT 1 COMMENT '作者等级（前端字段 authorLevel）',
    `author_bio`     VARCHAR(500)          DEFAULT NULL COMMENT '作者简介（前端字段 authorBio）',
    `publish_date`   DATETIME              DEFAULT NULL COMMENT '发布时间（前端字段 publishDate，格式 yyyy-MM-dd HH:mm:ss）',
    `description`    TEXT                  DEFAULT NULL COMMENT '视频简介',
    `external_link`  VARCHAR(500)          DEFAULT NULL COMMENT '外部播放地址（前端字段 externalLink）',
    `related_tags`   VARCHAR(500)          DEFAULT NULL COMMENT '关联标签，英文逗号分隔（前端字段 relatedTags -> string[]）',
    `related_anime`  VARCHAR(500)          DEFAULT NULL COMMENT '关联番剧 id，英文逗号分隔（前端字段 relatedAnime -> 对象数组）',
    `duration`       VARCHAR(20)           DEFAULT NULL COMMENT '时长文本，如 04:32（旧假数据里没有值，列先留着）',
    `view_count`     INT          NOT NULL DEFAULT 0 COMMENT '播放/浏览数',
    `like_count`     INT          NOT NULL DEFAULT 0 COMMENT '点赞数（由 LikeService 维护）',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_video_publish_date` (`publish_date`),
    KEY `idx_video_author` (`author`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频表';
