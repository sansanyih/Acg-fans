package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.entity.LikeRecord;
import com.example.acgdata.mapper.LikeRecordMapper;
import com.example.acgdata.security.SecurityUtils;
import com.example.acgdata.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 点赞服务实现。
 *
 * <p>设计要点：
 * <ol>
 *     <li>点赞记录写 like_record，靠唯一索引 (target_type, target_id, user_id) 防重；
 *         并发重复点赞会触发 DuplicateKeyException，这里转换成友好的业务异常；</li>
 *     <li>目标表的 like_count 采用冗余计数 + 原子自增，避免每次列表查询都 count(*) 聚合；</li>
 *     <li>表名来自白名单常量，不存在 SQL 注入风险。</li>
 * </ol>
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    /** targetType -> 表名白名单 */
    private static final Map<String, String> TARGET_TABLE = Map.of(
            "anime", "anime",
            "article", "article",
            "video", "video",
            "announcement", "announcement",
            "weekly", "weekly_report");

    private final LikeRecordMapper likeRecordMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long like(String targetType, Long targetId) {
        Long userId = SecurityUtils.getUserId();

        LikeRecord record = new LikeRecord();
        record.setTargetType(targetType);
        record.setTargetId(targetId);
        record.setUserId(userId);
        try {
            likeRecordMapper.insert(record);
        } catch (DuplicateKeyException e) {
            // 并发下两个请求同时通过 isLiked 检查时，唯一索引兜底
            throw new BusinessException(ResultCode.REPEAT_OPERATION, "已经点过赞了");
        }

        return updateLikeCount(targetType, targetId, 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long unlike(String targetType, Long targetId) {
        Long userId = SecurityUtils.getUserId();
        int deleted = likeRecordMapper.delete(Wrappers.<LikeRecord>lambdaQuery()
                .eq(LikeRecord::getTargetType, targetType)
                .eq(LikeRecord::getTargetId, targetId)
                .eq(LikeRecord::getUserId, userId));
        if (deleted == 0) {
            throw new BusinessException(ResultCode.OPERATION_FAILED, "还没有点过赞");
        }
        return updateLikeCount(targetType, targetId, -1);
    }

    @Override
    public long count(String targetType, Long targetId) {
        return likeRecordMapper.selectCount(Wrappers.<LikeRecord>lambdaQuery()
                .eq(LikeRecord::getTargetType, targetType)
                .eq(LikeRecord::getTargetId, targetId));
    }

    @Override
    public boolean isLiked(String targetType, Long targetId) {
        Long userId = SecurityUtils.getLoginUserOrNull() == null ? null : SecurityUtils.getUserId();
        if (userId == null) {
            return false;
        }
        return likeRecordMapper.selectCount(Wrappers.<LikeRecord>lambdaQuery()
                .eq(LikeRecord::getTargetType, targetType)
                .eq(LikeRecord::getTargetId, targetId)
                .eq(LikeRecord::getUserId, userId)) > 0;
    }

    /** 原子更新目标表点赞数并返回最新值 */
    private long updateLikeCount(String targetType, Long targetId, int delta) {
        String table = tableOf(targetType);
        jdbcTemplate.update(
                "UPDATE " + table + " SET like_count = GREATEST(like_count + ?, 0) WHERE id = ? AND deleted = 0",
                delta, targetId);
        Long count = jdbcTemplate.queryForObject(
                "SELECT like_count FROM " + table + " WHERE id = ?", Long.class, targetId);
        return count == null ? 0L : count;
    }

    private String tableOf(String targetType) {
        String table = TARGET_TABLE.get(targetType);
        if (table == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "不支持的点赞目标: " + targetType);
        }
        return table;
    }
}
