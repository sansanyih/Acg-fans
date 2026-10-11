package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.entity.Tag;

import java.util.List;

/**
 * 标签服务：番剧标签的读写入口，其它模块不要自己操作 tag / anime_tag 表。
 */
public interface TagService extends IService<Tag> {

    List<String> listNamesByAnimeId(Long animeId);

    /** 重新绑定番剧标签：先删旧关联，再按标签名查/建标签并建立关联 */
    void bindAnimeTags(Long animeId, List<String> tagNames);

    List<Tag> listByType(String type);
}
