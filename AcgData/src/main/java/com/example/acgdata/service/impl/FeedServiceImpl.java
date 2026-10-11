package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.FeedSaveDTO;
import com.example.acgdata.entity.Feed;
import com.example.acgdata.mapper.FeedMapper;
import com.example.acgdata.service.FeedService;
import com.example.acgdata.vo.FeedVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 动态服务实现。
 *
 * <p>排序说明：旧接口对 `time` 文案做字符串排序（日期格式倒序 + 「N天前」按天数正序），
 * 因为整点种子数据的日期本身就满足「倒序」这一条，这里统一按 publish_time 倒序 + id 倒序，
 * 结果顺序与旧接口一致（见 db/data/31_feed.sql 的注释）。
 */
@Service
public class FeedServiceImpl extends ServiceImpl<FeedMapper, Feed> implements FeedService {

    /** 新增动态时展示成「刚刚」，与旧接口一致 */
    private static final String JUST_NOW = "刚刚";

    /** 缺省跳转链接，与旧接口一致 */
    private static final String DEFAULT_LINK = "https://example.com/feed/new";

    @Override
    public List<FeedVO> listAll() {
        return list(Wrappers.<Feed>lambdaQuery()
                .orderByDesc(Feed::getPublishTime)
                .orderByDesc(Feed::getId))
                .stream()
                .map(FeedVO::from)
                .toList();
    }

    @Override
    public FeedVO getVO(Long id) {
        return FeedVO.from(getExisting(id));
    }

    @Override
    public List<FeedVO> search(String keyword) {
        String value = keyword == null ? "" : keyword.trim();
        return list(Wrappers.<Feed>lambdaQuery()
                .and(wrapper -> wrapper
                        .like(Feed::getContent, value)
                        .or().like(Feed::getUsername, value))
                .orderByDesc(Feed::getPublishTime)
                .orderByDesc(Feed::getId))
                .stream()
                .map(FeedVO::from)
                .toList();
    }

    @Override
    public FeedVO saveFeed(FeedSaveDTO dto) {
        Feed feed = new Feed();
        feed.setUserAvatar(dto.getUserAvatar());
        feed.setUsername(dto.getUsername());
        feed.setContent(dto.getContent());
        feed.setLink(dto.getLink() == null || dto.getLink().isBlank() ? DEFAULT_LINK : dto.getLink());
        feed.setPublishTime(LocalDateTime.now());
        feed.setTimeLabel(JUST_NOW);
        feed.setLikeCount(0);
        save(feed);
        return FeedVO.from(feed);
    }

    @Override
    public FeedVO updateFeed(Long id, FeedSaveDTO dto) {
        Feed feed = getExisting(id);
        // 旧接口的 update 会忽略 id / time，这里同理：只覆盖业务字段，时间文案保持不变
        feed.setUserAvatar(dto.getUserAvatar());
        feed.setUsername(dto.getUsername());
        feed.setContent(dto.getContent());
        feed.setLink(dto.getLink());
        updateById(feed);
        return FeedVO.from(feed);
    }

    @Override
    public void removeFeed(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic）
        removeById(id);
    }

    private Feed getExisting(Long id) {
        Feed feed = getById(id);
        if (feed == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return feed;
    }
}
