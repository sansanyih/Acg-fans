-- ============================================================
-- 角色表（character 模块）
-- 说明：所属番剧只用 anime_id 列关联，**不建外键约束**；
--      点赞数 / 踩数用冗余计数列（like_count、dislike_count），
--      其中 like_count 为全局约定列（每个业务表都要有）。
-- ============================================================
CREATE TABLE IF NOT EXISTS `character`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`               VARCHAR(100) NOT NULL COMMENT '角色名（中文）',
    `japanese_name`      VARCHAR(100)          DEFAULT NULL COMMENT '角色名（日文）',
    `image`              VARCHAR(500)          DEFAULT NULL COMMENT '角色图片地址',
    `anime_id`           BIGINT       NOT NULL COMMENT '所属番剧 id（逻辑关联 anime.id，不加外键）',
    `anime_title`        VARCHAR(200)          DEFAULT NULL COMMENT '所属番剧原名',
    `anime_chinese_title` VARCHAR(200)         DEFAULT NULL COMMENT '所属番剧中文名',
    `voice_actor`        VARCHAR(100)          DEFAULT NULL COMMENT '声优',
    `cv`                 VARCHAR(100)          DEFAULT NULL COMMENT 'CV（历史字段，与 voice_actor 同值）',
    `gender`             VARCHAR(10)           DEFAULT NULL COMMENT '性别',
    `age`                INT                   DEFAULT NULL COMMENT '年龄',
    `height`             VARCHAR(20)           DEFAULT NULL COMMENT '身高（带单位，如 195cm）',
    `weight`             VARCHAR(20)           DEFAULT NULL COMMENT '体重（带单位，如 82kg）',
    `birthday`           VARCHAR(10)           DEFAULT NULL COMMENT '生日，格式 MM-dd（前端按此格式比对）',
    `description`        TEXT                  DEFAULT NULL COMMENT '角色简介',
    `like_count`         INT          NOT NULL DEFAULT 0 COMMENT '点赞数（前端字段名 likes）',
    `dislike_count`      INT          NOT NULL DEFAULT 0 COMMENT '踩数（前端字段名 dislikes）',
    `create_time`        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`            TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_character_anime` (`anime_id`),
    KEY `idx_character_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';
