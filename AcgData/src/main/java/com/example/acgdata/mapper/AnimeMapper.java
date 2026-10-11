package com.example.acgdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.acgdata.entity.Anime;
import com.example.acgdata.mapper.row.AnimeListRow;
import com.example.acgdata.mapper.row.RatingListRow;
import com.example.acgdata.vo.AnimeDetailVO;
import com.example.acgdata.vo.AnimeItemVO;
import com.example.acgdata.vo.ViewingRecordVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

/**
 * 番剧 Mapper。
 *
 * <p>设计要点：列表接口**不允许 N+1**，所以标签用一条 LEFT JOIN 查询一次性取回，
 * 由 Service 在内存里按 anime_id 分组拼装；点赞数直接读 `anime.like_count` 冗余列。
 */
public interface AnimeMapper extends BaseMapper<Anime> {

    /** 基础列（含 update_time 格式化出来的 lastEdit） */
    String BASE_COLUMNS = """
            SELECT a.id,
                   a.title,
                   a.chinese_title,
                   a.image,
                   a.description,
                   a.introduction,
                   a.studio,
                   a.episodes,
                   a.start_date,
                   a.broadcast_day,
                   a.original_work,
                   a.director,
                   a.script,
                   a.storyboard,
                   a.performance,
                   a.music,
                   a.character_design,
                   a.series_composition,
                   a.art_director,
                   a.color_design,
                   a.chief_animation_director,
                   a.official_website,
                   a.like_count,
                   DATE_FORMAT(a.update_time, '%Y-%m-%d %H:%i:%s') AS last_edit
            FROM anime a
            """;

    /**
     * 按 id 集合批量查番剧基础信息，名称字段暂不填充（由 Service 转 VO 时补 "anime"）。
     */
    @Select("""
            <script>
            """
            + BASE_COLUMNS + """
            WHERE a.id IN
            <foreach collection="ids" item="id" open="(" separator="," close=")">#{id}</foreach>
            ORDER BY a.id ASC
            </script>
            """)
    List<AnimeItemVO> selectItemsByIds(@Param("ids") Collection<Long> ids);

    /**
     * 批量查番剧 + 标签（一个番剧有 N 个标签就有 N 行），Service 侧按行分组拼装标签列表。
     */
    @Select("""
            <script>
            """
            + BASE_COLUMNS + """
            LEFT JOIN anime_tag at ON at.anime_id = a.id
            LEFT JOIN tag t ON t.id = at.tag_id AND t.deleted = 0
            WHERE a.id IN
            <foreach collection="ids" item="id" open="(" separator="," close=")">#{id}</foreach>
            ORDER BY a.id ASC, t.id ASC
            </script>
            """)
    List<AnimeListRow> selectRowsWithTags(@Param("ids") Collection<Long> ids);

    /**
     * 详情主体：番剧全部字段 + 点赞数 + lastEdit。
     * 标签、观影记录、评论由 Service 再各查一次拼装（详情页不敏感，列表接口才禁止 N+1）。
     */
    @Select(BASE_COLUMNS + """
            WHERE a.id = #{id} AND a.deleted = 0
            """)
    AnimeDetailVO selectDetailById(@Param("id") Long id);

    /**
     * 按搜索关键字过滤番剧 id（标题 / 中文标题 / 描述 / 简介 / 制作公司 / 标签）。
     * 名称与标签都走 MySQL 的 utf8mb4 排序规则，天然大小写不敏感。
     */
    @Select("""
            <script>
            SELECT DISTINCT a.id
            FROM anime a
            LEFT JOIN anime_tag at ON at.anime_id = a.id
            LEFT JOIN tag t ON t.id = at.tag_id AND t.deleted = 0
            WHERE a.deleted = 0
              AND (
                LOWER(a.title) LIKE LOWER(CONCAT('%', #{keyword}, '%'))
                OR LOWER(a.chinese_title) LIKE LOWER(CONCAT('%', #{keyword}, '%'))
                OR LOWER(a.description) LIKE LOWER(CONCAT('%', #{keyword}, '%'))
                OR LOWER(a.introduction) LIKE LOWER(CONCAT('%', #{keyword}, '%'))
                OR LOWER(a.studio) LIKE LOWER(CONCAT('%', #{keyword}, '%'))
                OR LOWER(t.name) LIKE LOWER(CONCAT('%', #{keyword}, '%'))
              )
            ORDER BY a.id ASC
            </script>
            """)
    List<Long> selectIdsByKeyword(@Param("keyword") String keyword);

    /** 待播：开播日期晚于今天 */
    @Select("""
            SELECT a.id
            FROM anime a
            WHERE a.deleted = 0 AND a.start_date IS NOT NULL AND a.start_date > CURDATE()
            ORDER BY a.start_date ASC, a.id ASC
            """)
    List<Long> selectUpcomingIds();

    /** 最近更新：按更新时间倒序 */
    @Select("""
            SELECT a.id
            FROM anime a
            WHERE a.deleted = 0
            ORDER BY a.update_time DESC, a.id ASC
            LIMIT #{limit}
            """)
    List<Long> selectRecentUpdatedIds(@Param("limit") int limit);

    /** 最新开播：按开播日期倒序，没有日期的排在最后 */
    @Select("""
            SELECT a.id
            FROM anime a
            WHERE a.deleted = 0
            ORDER BY (a.start_date IS NULL) ASC, a.start_date DESC, a.id ASC
            LIMIT #{limit}
            """)
    List<Long> selectLatestIds(@Param("limit") int limit);

    /** 随机取若干番剧 id */
    @Select("""
            SELECT a.id
            FROM anime a
            WHERE a.deleted = 0
            ORDER BY RAND()
            LIMIT #{limit}
            """)
    List<Long> selectRandomIds(@Param("limit") int limit);

    /** 全部番剧标签名（去重，用于随机标签推荐） */
    @Select("""
            SELECT DISTINCT t.name
            FROM anime_tag at
            JOIN tag t ON t.id = at.tag_id AND t.deleted = 0
            JOIN anime a ON a.id = at.anime_id AND a.deleted = 0
            """)
    List<String> selectAllTagNames();

    /** 包含某个标签的全部番剧 id */
    @Select("""
            SELECT DISTINCT a.id
            FROM anime a
            JOIN anime_tag at ON at.anime_id = a.id
            JOIN tag t ON t.id = at.tag_id AND t.deleted = 0
            WHERE a.deleted = 0 AND t.name = #{tagName}
            ORDER BY a.id ASC
            """)
    List<Long> selectIdsByTagName(@Param("tagName") String tagName);

    /** 某番剧的全部观影记录（评分） */
    @Select("""
            SELECT r.id,
                   r.user,
                   r.user_avatar,
                   r.rating,
                   r.total_rating,
                   r.music_rating,
                   r.art_rating,
                   r.script_rating,
                   r.performance_rating,
                   r.program_rating,
                   r.voice_rating,
                   r.comment,
                   DATE_FORMAT(r.rate_date, '%Y-%m-%d %H:%i:%s') AS date
            FROM anime_rating r
            WHERE r.anime_id = #{animeId} AND r.deleted = 0
            ORDER BY r.rate_date DESC, r.id ASC
            """)
    List<ViewingRecordVO> selectRatingsByAnimeId(@Param("animeId") Long animeId);

    /** 全部观影记录 id（随机评分推荐用） */
    @Select("""
            SELECT r.id
            FROM anime_rating r
            JOIN anime a ON a.id = r.anime_id AND a.deleted = 0
            WHERE r.deleted = 0
            """)
    List<Long> selectAllRatingIds();

    /** 按 id 批量查观影记录，并带上所属番剧信息（对齐前端 RatingItem） */
    @Select("""
            <script>
            SELECT r.id,
                   r.user,
                   r.user_avatar,
                   r.rating,
                   r.total_rating,
                   r.music_rating,
                   r.art_rating,
                   r.script_rating,
                   r.performance_rating,
                   r.program_rating,
                   r.voice_rating,
                   r.comment,
                   DATE_FORMAT(r.rate_date, '%Y-%m-%d %H:%i:%s') AS date,
                   a.id           AS anime_id,
                   a.title        AS anime_title,
                   a.chinese_title AS anime_chinese_title
            FROM anime_rating r
            JOIN anime a ON a.id = r.anime_id AND a.deleted = 0
            WHERE r.deleted = 0 AND r.id IN
            <foreach collection="ids" item="id" open="(" separator="," close=")">#{id}</foreach>
            </script>
            """)
    List<RatingListRow> selectRatingRowsByIds(@Param("ids") Collection<Long> ids);
}
