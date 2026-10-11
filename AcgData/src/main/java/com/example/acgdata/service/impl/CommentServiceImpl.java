package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.entity.Comment;
import com.example.acgdata.entity.User;
import com.example.acgdata.mapper.CommentMapper;
import com.example.acgdata.mapper.UserMapper;
import com.example.acgdata.security.LoginUser;
import com.example.acgdata.security.SecurityUtils;
import com.example.acgdata.service.CommentService;
import com.example.acgdata.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论服务实现。
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final UserMapper userMapper;

    @Override
    public List<CommentVO> listByTarget(String targetType, Long targetId) {
        return baseMapper.selectVOByTarget(targetType, targetId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentVO add(String targetType, Long targetId, CommentSaveDTO dto) {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        Comment comment = new Comment();
        comment.setTargetType(targetType);
        comment.setTargetId(targetId);
        comment.setUserId(loginUser.getId());
        comment.setContent(dto.getContent());
        comment.setParentId(dto.getParentId());
        comment.setLikeCount(0);
        save(comment);

        // 维护用户的评论数（原子自增，避免读改写的并发问题）
        userMapper.update(null, Wrappers.<User>lambdaUpdate()
                .setSql("comment_count = comment_count + 1")
                .eq(User::getId, loginUser.getId()));

        return baseMapper.selectVOById(comment.getId());
    }

    @Override
    public long countByTarget(String targetType, Long targetId) {
        return count(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getTargetType, targetType)
                .eq(Comment::getTargetId, targetId));
    }
}
