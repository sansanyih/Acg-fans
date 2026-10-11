package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.entity.AnimeTag;
import com.example.acgdata.entity.Tag;
import com.example.acgdata.mapper.AnimeTagMapper;
import com.example.acgdata.mapper.TagMapper;
import com.example.acgdata.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签服务实现。
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private static final String ANIME = "anime";

    private final AnimeTagMapper animeTagMapper;

    @Override
    public List<String> listNamesByAnimeId(Long animeId) {
        if (animeId == null) {
            return List.of();
        }
        return animeTagMapper.selectTagNamesByAnimeId(animeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindAnimeTags(Long animeId, List<String> tagNames) {
        // 关联表是物理删除，重新绑定即清空旧关联
        animeTagMapper.delete(Wrappers.<AnimeTag>lambdaQuery().eq(AnimeTag::getAnimeId, animeId));

        if (tagNames == null || tagNames.isEmpty()) {
            return;
        }
        for (String tagName : tagNames) {
            if (!StringUtils.hasText(tagName)) {
                continue;
            }
            String name = tagName.trim();
            Tag tag = getOne(Wrappers.<Tag>lambdaQuery()
                    .eq(Tag::getName, name)
                    .eq(Tag::getType, ANIME), false);
            if (tag == null) {
                tag = new Tag();
                tag.setName(name);
                tag.setType(ANIME);
                save(tag);
            }
            AnimeTag relation = new AnimeTag();
            relation.setAnimeId(animeId);
            relation.setTagId(tag.getId());
            animeTagMapper.insert(relation);
        }
    }

    @Override
    public List<Tag> listByType(String type) {
        return list(Wrappers.<Tag>lambdaQuery()
                .eq(Tag::getType, type)
                .orderByAsc(Tag::getId));
    }
}
