package com.example.acgdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.acgdata.entity.EditStatistic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface EditStatisticMapper extends BaseMapper<EditStatistic> {

    /**
     * 编辑次数 +1：利用 stat_date + type 的唯一索引做 upsert，一条 SQL 完成。
     */
    @Update("""
            INSERT INTO edit_statistic (stat_date, type, edit_count)
            VALUES (#{statDate}, #{type}, 1)
            ON DUPLICATE KEY UPDATE edit_count = edit_count + 1
            """)
    int increment(@Param("statDate") java.time.LocalDate statDate, @Param("type") String type);
}
