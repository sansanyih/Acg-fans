package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.CharacterSaveDTO;
import com.example.acgdata.entity.Character;
import com.example.acgdata.mapper.CharacterMapper;
import com.example.acgdata.service.CharacterService;
import com.example.acgdata.service.EditStatisticService;
import com.example.acgdata.vo.CharacterAnimeItemVO;
import com.example.acgdata.vo.CharacterItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色服务实现。
 *
 * <p>点赞说明：LikeService 的表名白名单只支持 anime/article/video/announcement/weekly，
 * 角色不在其中，因此这里**直接原子更新 character.like_count**（like_count = like_count + 1），
 * 不去调用 LikeService，也不写 like_record。
 */
@Service
@RequiredArgsConstructor
public class CharacterServiceImpl extends ServiceImpl<CharacterMapper, Character> implements CharacterService {

    private final EditStatisticService editStatisticService;

    @Override
    public List<CharacterItemVO> listAll() {
        return list(Wrappers.<Character>lambdaQuery()
                .orderByAsc(Character::getId))
                .stream()
                .map(CharacterItemVO::from)
                .toList();
    }

    @Override
    public CharacterItemVO getVO(Long id) {
        return CharacterItemVO.from(getExisting(id));
    }

    @Override
    public List<CharacterAnimeItemVO> listByAnimeId(Long animeId) {
        return list(Wrappers.<Character>lambdaQuery()
                .eq(animeId != null, Character::getAnimeId, animeId)
                .orderByAsc(Character::getId))
                .stream()
                .map(entity -> new CharacterAnimeItemVO(
                        entity.getId(),
                        entity.getName(),
                        entity.getImage(),
                        entity.getVoiceActor(),
                        entity.getLikeCount() == null ? 0 : entity.getLikeCount()))
                .toList();
    }

    @Override
    public List<CharacterItemVO> search(String keyword) {
        String value = keyword == null ? "" : keyword.trim();
        return list(Wrappers.<Character>lambdaQuery()
                .and(wrapper -> wrapper
                        .like(Character::getName, value)
                        .or().like(Character::getJapaneseName, value)
                        .or().like(Character::getDescription, value)
                        .or().like(Character::getAnimeTitle, value)
                        .or().like(Character::getAnimeChineseTitle, value))
                .orderByAsc(Character::getId))
                .stream()
                .map(CharacterItemVO::from)
                .toList();
    }

    @Override
    public CharacterItemVO saveCharacter(CharacterSaveDTO dto) {
        Character character = new Character();
        apply(character, dto);
        // 新增时计数从 0 开始（与旧接口一致）
        character.setLikeCount(0);
        character.setDislikeCount(0);
        save(character);
        // 编辑统计：insert 成功后 +1
        editStatisticService.increment("character");
        return CharacterItemVO.from(character);
    }

    @Override
    public CharacterItemVO updateCharacter(Long id, CharacterSaveDTO dto) {
        Character character = getExisting(id);
        apply(character, dto);
        updateById(character);
        // 编辑统计：update 成功后 +1
        editStatisticService.increment("character");
        return CharacterItemVO.from(character);
    }

    @Override
    public void removeCharacter(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic）
        removeById(id);
    }

    @Override
    public CharacterItemVO like(Long id) {
        getExisting(id);
        // 原子自增，避免并发丢更新；用 Wrappers.<Character>lambdaUpdate() 让 setSql 的
        // 字符串原样进入 SQL（不会被当成属性名解析）；用两参数版 update(null, wrapper)
        // 是最兼容的调用方式（无实体、只按 wrapper 更新）
        update(null, Wrappers.<Character>lambdaUpdate()
                .setSql("like_count = like_count + 1")
                .eq(Character::getId, id));
        return getVO(id);
    }

    @Override
    public CharacterItemVO dislike(Long id) {
        getExisting(id);
        update(null, Wrappers.<Character>lambdaUpdate()
                .setSql("dislike_count = dislike_count + 1")
                .eq(Character::getId, id));
        return getVO(id);
    }

    private Character getExisting(Long id) {
        Character character = getById(id);
        if (character == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return character;
    }

    /** 只覆盖 DTO 里给了值的字段，null 表示「不修改」，与旧接口 putAll 的语义对齐 */
    private void apply(Character character, CharacterSaveDTO dto) {
        if (dto.getName() != null) {
            character.setName(dto.getName());
        }
        if (dto.getJapaneseName() != null) {
            character.setJapaneseName(dto.getJapaneseName());
        }
        if (dto.getImage() != null) {
            character.setImage(dto.getImage());
        }
        if (dto.getAnimeId() != null) {
            character.setAnimeId(dto.getAnimeId());
        }
        if (dto.getAnimeTitle() != null) {
            character.setAnimeTitle(dto.getAnimeTitle());
        }
        if (dto.getAnimeChineseTitle() != null) {
            character.setAnimeChineseTitle(dto.getAnimeChineseTitle());
        }
        if (dto.getVoiceActor() != null) {
            character.setVoiceActor(dto.getVoiceActor());
        }
        if (dto.getCv() != null) {
            character.setCv(dto.getCv());
        }
        if (dto.getGender() != null) {
            character.setGender(dto.getGender());
        }
        if (dto.getAge() != null) {
            character.setAge(dto.getAge());
        }
        if (dto.getHeight() != null) {
            character.setHeight(dto.getHeight());
        }
        if (dto.getWeight() != null) {
            character.setWeight(dto.getWeight());
        }
        if (dto.getBirthday() != null) {
            character.setBirthday(dto.getBirthday());
        }
        if (dto.getDescription() != null) {
            character.setDescription(dto.getDescription());
        }
    }
}
