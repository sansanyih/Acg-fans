package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.entity.EditStatistic;
import com.example.acgdata.mapper.EditStatisticMapper;
import com.example.acgdata.service.EditStatisticService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 编辑统计服务实现。
 */
@Service
public class EditStatisticServiceImpl extends ServiceImpl<EditStatisticMapper, EditStatistic>
        implements EditStatisticService {

    /** 统计维度，固定四类，保证前端拿到的对象四个字段都存在 */
    private static final List<String> TYPES = List.of("anime", "article", "video", "character");

    @Override
    public void increment(String type) {
        baseMapper.increment(LocalDate.now(), type);
    }

    @Override
    public Map<String, Map<String, Integer>> getAll() {
        return toMap(list(Wrappers.<EditStatistic>lambdaQuery()
                .orderByAsc(EditStatistic::getStatDate)
                .orderByAsc(EditStatistic::getId)));
    }

    @Override
    public Map<String, Map<String, Integer>> getRange(LocalDate startDate, LocalDate endDate) {
        return toMap(list(Wrappers.<EditStatistic>lambdaQuery()
                .ge(startDate != null, EditStatistic::getStatDate, startDate)
                .le(endDate != null, EditStatistic::getStatDate, endDate)
                .orderByAsc(EditStatistic::getStatDate)
                .orderByAsc(EditStatistic::getId)));
    }

    @Override
    public void initIfEmpty(int days) {
        if (count() > 0) {
            return;
        }
        // 固定随机种子，保证每次重建演示数据结果一致，方便截图和对比
        Random random = new Random(20260414L);
        LocalDate today = LocalDate.now();
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            for (String type : TYPES) {
                EditStatistic statistic = new EditStatistic();
                statistic.setStatDate(date);
                statistic.setType(type);
                statistic.setEditCount(random.nextInt(60) + 5);
                save(statistic);
            }
        }
    }

    private Map<String, Map<String, Integer>> toMap(List<EditStatistic> statistics) {
        Map<String, Map<String, Integer>> result = new LinkedHashMap<>();
        for (EditStatistic statistic : statistics) {
            Map<String, Integer> dayCounts = result.computeIfAbsent(
                    statistic.getStatDate().toString(),
                    key -> {
                        Map<String, Integer> empty = new LinkedHashMap<>();
                        TYPES.forEach(type -> empty.put(type, 0));
                        return empty;
                    });
            dayCounts.put(statistic.getType(), statistic.getEditCount());
        }
        return result;
    }
}
