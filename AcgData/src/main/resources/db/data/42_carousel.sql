-- ============================================================
-- 首页轮播图种子数据（把旧 CarouselController 里的硬编码假数据原样迁移到表里）
-- 幂等：显式主键 id + ON DUPLICATE KEY UPDATE，可反复执行。
-- ============================================================
INSERT INTO `carousel` (`id`, `url`, `title`, `sort`)
VALUES (1, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/1.png', '轮播图1', 1),
       (2, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/2.png', '轮播图2', 2),
       (3, 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/4.jpg', '轮播图3', 3)
ON DUPLICATE KEY UPDATE `url`   = VALUES(`url`),
                        `title` = VALUES(`title`),
                        `sort`  = VALUES(`sort`);
