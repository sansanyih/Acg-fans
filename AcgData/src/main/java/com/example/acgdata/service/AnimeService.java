package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.AnimeSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.entity.Anime;
import com.example.acgdata.vo.AnimeDetailVO;
import com.example.acgdata.vo.AnimeItemVO;
import com.example.acgdata.vo.CommentVO;
import com.example.acgdata.vo.RatingVO;

import java.util.List;
import java.util.Map;

/**
 * 番剧服务：列表 / 详情 / 搜索 / 随机推荐 / 随机评分 + 新增 / 修改 / 删除 / 评论。
 */
public interface AnimeService extends IService<Anime> {

    /** 全部番剧（列表项，含标签） */
    List<AnimeItemVO> listAll();

    /** 详情（番剧主体 + 标签 + 角色 + 观影记录 + 评论） */
    AnimeDetailVO getVO(Long id);

    /** 随机推荐，取 limit 条 */
    List<AnimeItemVO> listRandom(int limit);

    /** 最新开播（按 start_date 倒序），取 limit 条 */
    List<AnimeItemVO> listLatest(int limit);

    /** 待播：start_date 晚于今天，按开播日期升序 */
    List<AnimeItemVO> listUpcoming();

    /** 最近编辑：按 update_time 倒序，取 limit 条 */
    List<AnimeItemVO> listRecentUpdated(int limit);

    /**
     * 随机标签 + 该标签下的番剧列表。
     * 返回结构保持旧接口的 `{ tag, anime }`（tag 是字符串，anime 是列表）。
     */
    Map<String, Object> randomTag();

    /**
     * 随机的观影记录（评分），每条带上所属番剧信息（前端 RatingItem）。
     */
    List<RatingVO> randomRatings(int limit);

    /**
     * 关键字搜索番剧（标题 / 中文标题 / 描述 / 简介 / 制作公司 / 标签）。
     * ⚠️ 另一个模块的 SearchController 依赖这个签名，方法名与返回类型不能改。
     */
    List<AnimeItemVO> search(String keyword);

    /** 新增，成功后 edit_statistic 计数 +1 */
    AnimeDetailVO saveAnime(AnimeSaveDTO dto);

    /** 修改，成功后 edit_statistic 计数 +1 */
    AnimeDetailVO updateAnime(Long id, AnimeSaveDTO dto);

    /** 删除（逻辑删除） */
    void removeAnime(Long id);

    /** 发表评论，返回新评论（评论人取自 JWT） */
    CommentVO addComment(Long id, CommentSaveDTO dto);
}
