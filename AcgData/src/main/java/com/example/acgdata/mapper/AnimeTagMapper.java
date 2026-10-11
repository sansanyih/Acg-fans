package com.example.acgdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.acgdata.entity.AnimeTag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AnimeTagMapper extends BaseMapper<AnimeTag> {

    /**
     * 查某个番剧的标签名列表（关联 tag 表）。
     * 注意：anime_tag 是物理删除的纯关联表，**没有 deleted 列**，不要再拼 at.deleted 条件。
     */
    @Select("""
            SELECT t.name
            FROM anime_tag at
            JOIN tag t ON t.id = at.tag_id AND t.deleted = 0
            WHERE at.anime_id = #{animeId}
            ORDER BY t.id
            """)
    List<String> selectTagNamesByAnimeId(@Param("animeId") Long animeId);
}
