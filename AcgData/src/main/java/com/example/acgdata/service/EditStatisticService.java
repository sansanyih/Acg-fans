package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.entity.EditStatistic;

import java.time.LocalDate;
import java.util.Map;

/**
 * 编辑统计服务：原来存在静态 Map 里的假数据改为落库，
 * 按「日期 + 类型」聚合，返回结构保持 日期 -> {anime, article, video, character}。
 */
public interface EditStatisticService extends IService<EditStatistic> {

    /** 业务模块新增 / 修改成功后调用，内部是 upsert 原子自增 */
    void increment(String type);

    Map<String, Map<String, Integer>> getAll();

    Map<String, Map<String, Integer>> getRange(LocalDate startDate, LocalDate endDate);

    /** 首次启动时生成最近 N 天的演示数据（已有数据则跳过） */
    void initIfEmpty(int days);
}
