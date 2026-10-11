-- ============================================================
-- 番剧模块（anime）
-- 约定：所有业务表统一带 create_time、update_time、deleted（逻辑删除）；
--      脚本幂等（CREATE TABLE IF NOT EXISTS），可重复启动。
-- ============================================================

-- 番剧表 ------------------------------------------------------
CREATE TABLE IF NOT EXISTS `anime`
(
    `id`                       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`                    VARCHAR(200) NOT NULL COMMENT '日文原名',
    `chinese_title`            VARCHAR(200)          DEFAULT NULL COMMENT '中文译名',
    `image`                    VARCHAR(500)          DEFAULT NULL COMMENT '封面图地址',
    `description`              VARCHAR(500)          DEFAULT NULL COMMENT '短描述（列表页展示）',
    `introduction`             TEXT                  DEFAULT NULL COMMENT '详细介绍（详情页展示）',
    `studio`                   VARCHAR(200)          DEFAULT NULL COMMENT '制作公司',
    `episodes`                 INT          NOT NULL DEFAULT 0 COMMENT '集数',
    `start_date`               DATE                  DEFAULT NULL COMMENT '开播日期',
    `broadcast_day`            VARCHAR(20)           DEFAULT NULL COMMENT '播出星期，例如「星期四」「不定期」',
    `original_work`            VARCHAR(200)          DEFAULT NULL COMMENT '原作',
    `director`                 VARCHAR(200)          DEFAULT NULL COMMENT '监督',
    `script`                   VARCHAR(200)          DEFAULT NULL COMMENT '脚本',
    `storyboard`               VARCHAR(200)          DEFAULT NULL COMMENT '分镜',
    `performance`              VARCHAR(200)          DEFAULT NULL COMMENT '演出',
    `music`                    VARCHAR(200)          DEFAULT NULL COMMENT '音乐',
    `character_design`         VARCHAR(200)          DEFAULT NULL COMMENT '人物设计',
    `series_composition`       VARCHAR(200)          DEFAULT NULL COMMENT '系列构成',
    `art_director`             VARCHAR(200)          DEFAULT NULL COMMENT '美术监督',
    `color_design`             VARCHAR(200)          DEFAULT NULL COMMENT '色彩设计',
    `chief_animation_director` VARCHAR(200)          DEFAULT NULL COMMENT '总作画监督',
    `official_website`         VARCHAR(500)          DEFAULT NULL COMMENT '官方网站',
    -- 点赞数由 LikeService 原子维护（LikeService 支持 anime → anime），业务代码只读不写
    `like_count`               INT          NOT NULL DEFAULT 0 COMMENT '点赞数',
    `sort`                     INT          NOT NULL DEFAULT 0 COMMENT '排序值，越小越靠前',
    `create_time`              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 同时充当旧接口的 lastEdit：每次修改都会被自动填充刷新
    `update_time`              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / 最近编辑时间',
    `deleted`                  TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_anime_title` (`title`),
    KEY `idx_anime_start_date` (`start_date`),
    KEY `idx_anime_update_time` (`update_time`),
    KEY `idx_anime_sort` (`sort`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='番剧表';

-- 番剧观影记录 / 评分表 ----------------------------------------
-- 对应旧接口详情里的 viewingRecords 与 /api/anime/get/random-ratings
CREATE TABLE IF NOT EXISTS `anime_rating`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `anime_id`           BIGINT       NOT NULL COMMENT '番剧 id',
    `user`               VARCHAR(50)  NOT NULL DEFAULT '匿名用户' COMMENT '评分人昵称',
    `user_avatar`        VARCHAR(500)          DEFAULT NULL COMMENT '评分人头像',
    `rating`             DECIMAL(4, 1) NOT NULL DEFAULT 0.0 COMMENT '总评分',
    `total_rating`       INT          NOT NULL DEFAULT 10 COMMENT '总分（满分）',
    `music_rating`       INT          NOT NULL DEFAULT 0 COMMENT '音乐',
    `art_rating`         INT          NOT NULL DEFAULT 0 COMMENT '美术',
    `script_rating`      INT          NOT NULL DEFAULT 0 COMMENT '剧本',
    `performance_rating` INT          NOT NULL DEFAULT 0 COMMENT '演出',
    `program_rating`     INT          NOT NULL DEFAULT 0 COMMENT '节目',
    `voice_rating`       INT          NOT NULL DEFAULT 0 COMMENT '声优',
    `comment`            VARCHAR(1000)         DEFAULT NULL COMMENT '短评',
    `rate_date`          DATETIME              DEFAULT NULL COMMENT '评分时间',
    -- 同一部番剧内的稳定序号，让种子 SQL 具备可用于 ON DUPLICATE KEY UPDATE 的唯一键
    `sort_order`         INT          NOT NULL DEFAULT 0 COMMENT '番剧内的记录序号',
    `create_time`        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`            TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_anime_rating_anime_sort` (`anime_id`, `sort_order`),
    KEY `idx_anime_rating_anime` (`anime_id`),
    KEY `idx_anime_rating_rate_date` (`rate_date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='番剧观影记录/评分表';

-- 说明：番剧与标签的多对多关联表 `anime_tag` 属于公共部分，见 db/schema/00_common.sql
--      （纯关联表使用物理删除，避免「逻辑删除 + 唯一索引」在反复绑定/解绑时主键冲突）
