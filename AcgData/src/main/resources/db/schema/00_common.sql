-- ============================================================
-- 公共表：用户 / 标签 / 关联 / 评论 / 点赞 / 评分 / 编辑统计
-- 约定：所有业务表统一带 create_time、update_time、deleted（逻辑删除）
--      脚本幂等（CREATE TABLE IF NOT EXISTS），可重复启动
-- ============================================================

-- 用户表 ------------------------------------------------------
CREATE TABLE IF NOT EXISTS `user`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`      VARCHAR(50)  NOT NULL COMMENT '用户名',
    `password`      VARCHAR(100) NOT NULL COMMENT 'BCrypt 加密后的密码（永不返回给前端）',
    `email`         VARCHAR(100) NOT NULL COMMENT '邮箱',
    `avatar`        VARCHAR(500)          DEFAULT NULL COMMENT '头像地址',
    `register_date` DATE                  DEFAULT NULL COMMENT '注册日期',
    `last_login`    DATETIME              DEFAULT NULL COMMENT '最近登录时间',
    `article_count` INT          NOT NULL DEFAULT 0 COMMENT '发布内容数',
    `comment_count` INT          NOT NULL DEFAULT 0 COMMENT '评论数',
    `bio`           VARCHAR(255)          DEFAULT NULL COMMENT '个人简介',
    `level`         INT          NOT NULL DEFAULT 1 COMMENT '用户等级',
    `is_admin`      TINYINT      NOT NULL DEFAULT 0 COMMENT '是否管理员：0-否 1-是',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_username` (`username`),
    UNIQUE KEY `uk_user_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 标签表 ------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tag`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(50) NOT NULL COMMENT '标签名',
    `type`        VARCHAR(20) NOT NULL DEFAULT 'anime' COMMENT '标签类型：anime/article/video',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     TINYINT     NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tag_name_type` (`name`, `type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='标签表';

-- 番剧-标签关联表 ---------------------------------------------
-- 说明：纯关联表使用物理删除（重新绑定标签时直接删旧记录），
--      避免「逻辑删除 + 唯一索引」在反复绑定/解绑时产生主键冲突
CREATE TABLE IF NOT EXISTS `anime_tag`
(
    `id`          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `anime_id`    BIGINT   NOT NULL COMMENT '番剧 id',
    `tag_id`      BIGINT   NOT NULL COMMENT '标签 id',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_anime_tag` (`anime_id`, `tag_id`),
    KEY `idx_anime_tag_tag` (`tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='番剧标签关联表';

-- 评论表（多态：番剧 / 文章 / 视频 / 公告 / 周报共用）----------
CREATE TABLE IF NOT EXISTS `comment`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `target_type` VARCHAR(20) NOT NULL COMMENT '目标类型：anime/article/video/announcement/weekly',
    `target_id`   BIGINT      NOT NULL COMMENT '目标 id',
    `user_id`     BIGINT      NOT NULL COMMENT '评论人 id',
    `content`     TEXT        NOT NULL COMMENT '评论内容',
    `like_count`  INT         NOT NULL DEFAULT 0 COMMENT '评论点赞数',
    `parent_id`   BIGINT               DEFAULT NULL COMMENT '父评论 id（楼中楼）',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     TINYINT     NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY `idx_comment_target` (`target_type`, `target_id`, `deleted`),
    KEY `idx_comment_user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='评论表';

-- 点赞记录表 --------------------------------------------------
-- 说明：点赞记录使用物理删除（取消点赞即删除记录），
--      避免「逻辑删除 + 唯一索引」导致同一用户重复记录冲突
CREATE TABLE IF NOT EXISTS `like_record`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `target_type` VARCHAR(20) NOT NULL COMMENT '目标类型',
    `target_id`   BIGINT      NOT NULL COMMENT '目标 id',
    `user_id`     BIGINT      NOT NULL COMMENT '点赞人 id',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_like_target_user` (`target_type`, `target_id`, `user_id`),
    KEY `idx_like_user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='点赞记录表';

-- 说明：番剧评分（观影记录）表属于 anime 模块，见 db/schema/10_anime.sql

-- 编辑统计表（按天 + 类型聚合）--------------------------------
CREATE TABLE IF NOT EXISTS `edit_statistic`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `stat_date`   DATE        NOT NULL COMMENT '统计日期',
    `type`        VARCHAR(20) NOT NULL COMMENT '类型：anime/article/video/character',
    `edit_count`  INT         NOT NULL DEFAULT 0 COMMENT '编辑次数',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     TINYINT     NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_type` (`stat_date`, `type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='编辑统计表';
