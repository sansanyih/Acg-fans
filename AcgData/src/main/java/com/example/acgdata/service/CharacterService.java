package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.CharacterSaveDTO;
import com.example.acgdata.entity.Character;
import com.example.acgdata.vo.CharacterAnimeItemVO;
import com.example.acgdata.vo.CharacterItemVO;

import java.util.List;

/**
 * 角色服务。
 *
 * <p>{@link #search(String)} 是跨模块（SearchController 的聚合搜索）依赖的公共方法，签名不能改。
 */
public interface CharacterService extends IService<Character> {

    /** GET /api/character/get：列表，返回角色完整信息（首页「今日生日」需要 birthday / description） */
    List<CharacterItemVO> listAll();

    /** GET /api/character/get/{id}：单个角色完整信息 */
    CharacterItemVO getVO(Long id);

    /** GET /api/character/getByAnime/{animeId}：某番剧下的角色列表 */
    List<CharacterAnimeItemVO> listByAnimeId(Long animeId);

    /** GET /api/character/search?keyword=：跨模块聚合搜索用，字段见 CharacterItemVO */
    List<CharacterItemVO> search(String keyword);

    /** POST /api/character/insert */
    CharacterItemVO saveCharacter(CharacterSaveDTO dto);

    /** PUT /api/character/update/{id} */
    CharacterItemVO updateCharacter(Long id, CharacterSaveDTO dto);

    /** DELETE /api/character/delete/{id}（逻辑删除） */
    void removeCharacter(Long id);

    /** POST /api/character/like/{id}：返回最新点赞数 */
    CharacterItemVO like(Long id);

    /** POST /api/character/dislike/{id}：返回最新踩数 */
    CharacterItemVO dislike(Long id);
}
