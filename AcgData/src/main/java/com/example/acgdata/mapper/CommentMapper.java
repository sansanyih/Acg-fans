package com.example.acgdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.acgdata.entity.Comment;
import com.example.acgdata.vo.CommentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    String VO_COLUMNS = """
            SELECT c.id,
                   c.content,
                   c.like_count,
                   DATE_FORMAT(c.create_time, '%Y-%m-%d %H:%i') AS date,
                   u.username AS user,
                   u.avatar   AS user_avatar
            FROM comment c
            LEFT JOIN user u ON u.id = c.user_id AND u.deleted = 0
            """;

    /**
     * 查某个目标下的评论，关联出评论人昵称与头像。
     * user_avatar / like_count 会按驼峰自动映射到 CommentVO 的 userAvatar / likeCount。
     */
    @Select(VO_COLUMNS + """
            WHERE c.target_type = #{targetType} AND c.target_id = #{targetId} AND c.deleted = 0
            ORDER BY c.create_time DESC, c.id DESC
            """)
    List<CommentVO> selectVOByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    @Select(VO_COLUMNS + "WHERE c.id = #{id} AND c.deleted = 0")
    CommentVO selectVOById(@Param("id") Long id);
}
