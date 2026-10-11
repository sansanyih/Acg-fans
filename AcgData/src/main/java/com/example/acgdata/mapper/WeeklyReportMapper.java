package com.example.acgdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.acgdata.entity.WeeklyReport;
import com.example.acgdata.vo.WeeklyItemVO;
import com.example.acgdata.vo.WeeklyReportVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 周报 Mapper。
 * 列表 / 详情查询直接用 DATE_FORMAT 把日期算成字符串，
 * 避免依赖 Jackson 的默认时间序列化（默认会输出带 T 的 ISO 格式）。
 */
public interface WeeklyReportMapper extends BaseMapper<WeeklyReport> {

    /** 周报列表（旧接口 /get 只返回最近的 5 条，按发布日期倒序） */
    @Select("""
            SELECT id,
                   cover,
                   title,
                   summary,
                   author,
                   author_avatar,
                   DATE_FORMAT(publish_date, '%Y-%m-%d') AS publish_date,
                   read_count,
                   like_count
            FROM weekly_report
            WHERE deleted = 0
            ORDER BY publish_date DESC, id DESC
            LIMIT 5
            """)
    List<WeeklyItemVO> selectListVO();

    /** 周报详情（含正文与 lastEdit = update_time，格式 yyyy年M月d日 HH:mm:ss） */
    @Select("""
            SELECT id,
                   cover,
                   title,
                   content,
                   summary,
                   author,
                   author_avatar,
                   DATE_FORMAT(publish_date, '%Y-%m-%d') AS publish_date,
                   read_count,
                   like_count,
                   DATE_FORMAT(update_time, '%Y年%c月%d日 %H:%i:%s') AS last_edit
            FROM weekly_report
            WHERE id = #{id} AND deleted = 0
            """)
    WeeklyReportVO selectVOById(@Param("id") Long id);
}
