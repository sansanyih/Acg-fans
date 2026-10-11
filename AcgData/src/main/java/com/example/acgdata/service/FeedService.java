package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.FeedSaveDTO;
import com.example.acgdata.entity.Feed;
import com.example.acgdata.vo.FeedVO;

import java.util.List;

/**
 * 动态服务。
 */
public interface FeedService extends IService<Feed> {

    /** GET /api/feed/get：按发布时间倒序（等价于旧接口的时间排序规则） */
    List<FeedVO> listAll();

    /** GET /api/feed/get/{id} */
    FeedVO getVO(Long id);

    /** GET /api/feed/search?keyword=：内容或发布者名包含关键字 */
    List<FeedVO> search(String keyword);

    /** POST /api/feed/insert：time 固定为「刚刚」 */
    FeedVO saveFeed(FeedSaveDTO dto);

    /** PUT /api/feed/update/{id}：id 与 time 不可改 */
    FeedVO updateFeed(Long id, FeedSaveDTO dto);

    /** DELETE /api/feed/delete/{id}（逻辑删除） */
    void removeFeed(Long id);
}
