package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.dto.WeeklySaveDTO;
import com.example.acgdata.entity.WeeklyReport;
import com.example.acgdata.mapper.WeeklyReportMapper;
import com.example.acgdata.service.CommentService;
import com.example.acgdata.service.LikeService;
import com.example.acgdata.service.WeeklyReportService;
import com.example.acgdata.vo.WeeklyCommentVO;
import com.example.acgdata.vo.WeeklyItemVO;
import com.example.acgdata.vo.WeeklyReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 周报服务实现。
 * 点赞计数完全交给 LikeService（它内部维护 weekly_report.like_count），这里不再自己加减。
 */
@Service
@RequiredArgsConstructor
public class WeeklyReportServiceImpl extends ServiceImpl<WeeklyReportMapper, WeeklyReport> implements WeeklyReportService {

    /** 评论 / 点赞的目标类型，取值见 REFACTOR_GUIDE §7.2（weekly 映射到 weekly_report 表） */
    private static final String TARGET_TYPE = "weekly";

    private static final String DEFAULT_COVER = "https://example.com/default-cover.jpg";
    private static final String DEFAULT_AUTHOR = "系统管理员";
    private static final String DEFAULT_AUTHOR_AVATAR = "https://example.com/admin-avatar.jpg";

    private final CommentService commentService;
    private final LikeService likeService;

    @Override
    public List<WeeklyItemVO> listItem() {
        return baseMapper.selectListVO();
    }

    @Override
    public WeeklyReportVO getVO(Long id) {
        getExisting(id);
        WeeklyReportVO vo = baseMapper.selectVOById(id);
        vo.setComments(listComments(id));
        return vo;
    }

    @Override
    public WeeklyReportVO saveReport(WeeklySaveDTO dto) {
        WeeklyReport report = new WeeklyReport();
        apply(report, dto);
        // 与旧接口一致：新周报的阅读量 / 点赞数归零，发布日期取当天
        report.setReadCount(0);
        report.setLikeCount(0);
        report.setPublishDate(LocalDate.now());
        save(report);
        return getVO(report.getId());
    }

    @Override
    public WeeklyReportVO updateReport(Long id, WeeklySaveDTO dto) {
        WeeklyReport report = getExisting(id);
        apply(report, dto);
        updateById(report);
        return getVO(id);
    }

    @Override
    public void removeReport(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic）
        removeById(id);
    }

    @Override
    public WeeklyCommentVO addComment(Long id, CommentSaveDTO dto) {
        getExisting(id);
        return WeeklyCommentVO.from(commentService.add(TARGET_TYPE, id, dto));
    }

    @Override
    public long like(Long id) {
        getExisting(id);
        return likeService.like(TARGET_TYPE, id);
    }

    /** 查不到统一抛业务异常（不要返回 null，也不要自己 try/catch） */
    private WeeklyReport getExisting(Long id) {
        WeeklyReport report = getById(id);
        if (report == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return report;
    }

    /** 评论列表：共享 CommentService 直接返回 CommentVO，再翻译成旧接口的字段结构 */
    private List<WeeklyCommentVO> listComments(Long id) {
        return commentService.listByTarget(TARGET_TYPE, id).stream()
                .map(WeeklyCommentVO::from)
                .toList();
    }

    /**
     * DTO -> 实体。
     * 更新场景下只覆盖 title / content / summary / cover / author / authorAvatar，
     * id、readCount、likeCount、publishDate 一律保留（与旧接口行为一致）。
     */
    private void apply(WeeklyReport report, WeeklySaveDTO dto) {
        report.setTitle(dto.getTitle());
        report.setContent(dto.getContent());
        report.setSummary(dto.getSummary());
        report.setCover(isBlank(dto.getCover()) ? DEFAULT_COVER : dto.getCover());
        report.setAuthor(isBlank(dto.getAuthor()) ? DEFAULT_AUTHOR : dto.getAuthor());
        report.setAuthorAvatar(isBlank(dto.getAuthorAvatar()) ? DEFAULT_AUTHOR_AVATAR : dto.getAuthorAvatar());
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
