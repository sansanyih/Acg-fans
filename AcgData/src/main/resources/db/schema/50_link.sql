-- ============================================================
-- 友情链接表
-- ============================================================
CREATE TABLE IF NOT EXISTS `link`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(50)  NOT NULL COMMENT '网站名称',
    `icon_url`    VARCHAR(500)          DEFAULT NULL COMMENT '图标地址（前端字段名 url）',
    `target_url`  VARCHAR(500) NOT NULL COMMENT '跳转地址（前端字段名 link）',
    `sort`        INT          NOT NULL DEFAULT 0 COMMENT '排序值，越小越靠前',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_link_sort` (`sort`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='友情链接表';
