package com.example.acgdata.service;

/**
 * 点赞服务：唯一索引防重 + 目标表计数维护。
 */
public interface LikeService {

    /** 点赞，返回目标最新点赞数；重复点赞抛 BusinessException(REPEAT_OPERATION) */
    long like(String targetType, Long targetId);

    /** 取消点赞，返回目标最新点赞数 */
    long unlike(String targetType, Long targetId);

    long count(String targetType, Long targetId);

    boolean isLiked(String targetType, Long targetId);
}
