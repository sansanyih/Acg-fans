package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.entity.Comment;
import com.example.acgdata.vo.CommentVO;

import java.util.List;

/**
 * 评论服务：番剧 / 文章 / 视频 / 公告 / 周报共用一套多态评论。
 */
public interface CommentService extends IService<Comment> {

    List<CommentVO> listByTarget(String targetType, Long targetId);

    /** 发表评论，评论人取自 JWT */
    CommentVO add(String targetType, Long targetId, CommentSaveDTO dto);

    long countByTarget(String targetType, Long targetId);
}
