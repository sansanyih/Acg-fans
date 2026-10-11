package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.dto.VideoSaveDTO;
import com.example.acgdata.entity.Video;
import com.example.acgdata.vo.CommentVO;
import com.example.acgdata.vo.VideoDetailVO;
import com.example.acgdata.vo.VideoItemVO;

import java.util.List;

/**
 * 视频服务。
 */
public interface VideoService extends IService<Video> {

    /** 列表（按发布时间倒序）：GET /api/video/get */
    List<VideoItemVO> listAll();

    /** 详情（含评论）：GET /api/video/get/{id} */
    VideoDetailVO getDetail(Long id);

    /**
     * 关键词搜索：GET /api/video/search?keyword=
     * 供 SearchController 复用，返回类型不可更改。
     */
    List<VideoItemVO> search(String keyword);

    /** 新增：POST /api/video/insert */
    VideoDetailVO saveVideo(VideoSaveDTO dto);

    /** 修改：PUT /api/video/update/{id} */
    VideoDetailVO updateVideo(Long id, VideoSaveDTO dto);

    /** 逻辑删除：DELETE /api/video/delete/{id} */
    void removeVideo(Long id);

    /** 发表评论：POST /api/video/addComment/{id} */
    CommentVO addComment(Long id, CommentSaveDTO dto);
}
