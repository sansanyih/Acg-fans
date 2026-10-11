package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.dto.WeeklySaveDTO;
import com.example.acgdata.entity.WeeklyReport;
import com.example.acgdata.vo.WeeklyCommentVO;
import com.example.acgdata.vo.WeeklyItemVO;
import com.example.acgdata.vo.WeeklyReportVO;

import java.util.List;

/**
 * 周报服务。
 * 评论走共享 CommentService（targetType = weekly），
 * 点赞走共享 LikeService（targetType = weekly，内部维护 weekly_report.like_count）。
 */
public interface WeeklyReportService extends IService<WeeklyReport> {

    /** 周报列表（旧接口只返回最近 5 条，按发布日期倒序） */
    List<WeeklyItemVO> listItem();

    /** 周报详情（含正文、lastEdit、评论列表） */
    WeeklyReportVO getVO(Long id);

    /** 新增周报，返回保存后的完整对象 */
    WeeklyReportVO saveReport(WeeklySaveDTO dto);

    /** 修改周报，返回更新后的完整对象（lastEdit 会刷新成当前时间） */
    WeeklyReportVO updateReport(Long id, WeeklySaveDTO dto);

    /** 逻辑删除周报，查不到抛业务异常 */
    void removeReport(Long id);

    /** 发表评论，评论人取自 JWT */
    WeeklyCommentVO addComment(Long id, CommentSaveDTO dto);

    /** 点赞，返回最新点赞数；重复点赞抛业务异常 */
    long like(Long id);
}
