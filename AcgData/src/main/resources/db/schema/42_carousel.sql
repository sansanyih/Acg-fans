-- ============================================================
-- 首页轮播图表（carousel 模块）
-- 约定：统一带 create_time / update_time / deleted（逻辑删除）。
--      该表不参与点赞 / 评论，但按铁律 6 仍保留三件套。
-- ============================================================
CREATE TABLE IF NOT EXISTS `carousel`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `url`         VARCHAR(500) NOT NULL COMMENT '图片地址（前端字段 url）',
    `title`       VARCHAR(200) NOT NULL COMMENT '标题（前端字段 title）',
    `sort`        INT          NOT NULL DEFAULT 0 COMMENT '排序值，越小越靠前',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_carousel_sort` (`sort`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='首页轮播图表';
