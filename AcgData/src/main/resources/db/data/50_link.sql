-- ============================================================
-- 友情链接种子数据（幂等：显式主键 + ON DUPLICATE KEY UPDATE）
-- ============================================================
INSERT INTO `link` (`id`, `name`, `icon_url`, `target_url`, `sort`)
VALUES (1, 'Bangumi番组计划', 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/bangumi.png', 'https://chii.in', 1),
       (2, 'WebGAL', 'https://webgal.ink', 'https://webgal.ink', 2),
       (3, 'HumiHumi', 'https://humihumi.org', 'https://humihumi.org', 3),
       (4, '末语引擎', 'https://moyu.moe', 'https://moyu.moe', 4),
       (5, 'Nanovel(中文站)', 'https://nanovel.net', 'https://nanovel.net', 5),
       (6, 'NGAVG', 'https://ngavg.org', 'https://ngavg.org', 6),
       (7, 'Lovestory Project', 'https://lsp.moe', 'https://lsp.moe', 7),
       (8, '月幕GalGame', 'https://tsukimi.moe', 'https://tsukimi.moe', 8),
       (9, 'Steam上的中文Galgame', 'https://store.steampowered.com', 'https://store.steampowered.com', 9),
       (10, '豆津账本', 'https://doujin.moe', 'https://doujin.moe', 10),
       (11, '123制作组', '#', '#', 11),
       (12, '花舞导航', '#', '#', 12)
ON DUPLICATE KEY UPDATE `name`       = VALUES(`name`),
                        `icon_url`   = VALUES(`icon_url`),
                        `target_url` = VALUES(`target_url`),
                        `sort`       = VALUES(`sort`);
