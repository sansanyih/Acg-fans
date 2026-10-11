package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.dto.VideoSaveDTO;
import com.example.acgdata.entity.Video;
import com.example.acgdata.mapper.VideoMapper;
import com.example.acgdata.service.CommentService;
import com.example.acgdata.service.EditStatisticService;
import com.example.acgdata.service.VideoService;
import com.example.acgdata.vo.CommentVO;
import com.example.acgdata.vo.VideoAnimeItemVO;
import com.example.acgdata.vo.VideoDetailVO;
import com.example.acgdata.vo.VideoItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 视频服务实现。
 *
 * <p>依赖的评论 / 编辑统计都是共享服务，这里只做编排：
 * 评论走 {@link CommentService}（targetType = "video"），
 * 新增 / 修改成功后调用 {@link EditStatisticService#increment(String)}（type = "video"）。
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    /** 多态评论的目标类型 */
    private static final String TARGET_TYPE = "video";

    private static final String DEFAULT_THUMBNAIL =
            "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_1.png";

    private static final String DEFAULT_AVATAR =
            "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/default-avatar.jpg";

    private static final String DEFAULT_AUTHOR_BIO = "这个作者很懒，还没有填写自我介绍";

    private static final String DEFAULT_AUTHOR_ROLE = "创作者";

    private final CommentService commentService;
    private final EditStatisticService editStatisticService;

    @Override
    public List<VideoItemVO> listAll() {
        // 旧接口按 publishDate 字符串倒序，这里等价的 SQL 排序（只算 publish_date，date 部分即可）
        return list(Wrappers.<Video>lambdaQuery()
                .orderByDesc(Video::getPublishDate)
                .orderByDesc(Video::getId))
                .stream()
                .map(VideoItemVO::from)
                .toList();
    }

    @Override
    public VideoDetailVO getDetail(Long id) {
        return toDetail(getExisting(id));
    }

    @Override
    public List<VideoItemVO> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return List.of();
        }
        String kw = keyword.trim();
        return list(Wrappers.<Video>lambdaQuery()
                .and(w -> w.like(Video::getTitle, kw)
                        .or().like(Video::getDescription, kw)
                        .or().like(Video::getAuthor, kw)
                        .or().like(Video::getRelatedTags, kw))
                .orderByDesc(Video::getPublishDate)
                .orderByDesc(Video::getId))
                .stream()
                .map(VideoItemVO::fromSearch)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VideoDetailVO saveVideo(VideoSaveDTO dto) {
        Video video = new Video();
        apply(video, dto);
        video.setThumbnail(StringUtils.hasText(dto.getThumbnail()) ? dto.getThumbnail() : DEFAULT_THUMBNAIL);
        video.setAuthorAvatar(StringUtils.hasText(dto.getAuthorAvatar()) ? dto.getAuthorAvatar() : DEFAULT_AVATAR);
        video.setAuthorBio(StringUtils.hasText(dto.getAuthorBio()) ? dto.getAuthorBio() : DEFAULT_AUTHOR_BIO);
        video.setAuthorRole(StringUtils.hasText(dto.getAuthorRole()) ? dto.getAuthorRole() : DEFAULT_AUTHOR_ROLE);
        video.setAuthorLevel(dto.getAuthorLevel() == null ? 1 : dto.getAuthorLevel());
        if (video.getPublishDate() == null) {
            video.setPublishDate(LocalDateTime.now());
        }
        video.setViewCount(0);
        video.setLikeCount(0);
        save(video);

        editStatisticService.increment(TARGET_TYPE);
        return VideoDetailVO.from(video);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VideoDetailVO updateVideo(Long id, VideoSaveDTO dto) {
        Video video = getExisting(id);
        apply(video, dto);
        updateById(video);

        editStatisticService.increment(TARGET_TYPE);
        return toDetail(video);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeVideo(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic）
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentVO addComment(Long id, CommentSaveDTO dto) {
        getExisting(id);
        return commentService.add(TARGET_TYPE, id, dto);
    }

    /** 查不到直接抛业务异常，由全局异常处理器收敛 */
    private Video getExisting(Long id) {
        Video video = getById(id);
        if (video == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return video;
    }

    private VideoDetailVO toDetail(Video video) {
        VideoDetailVO vo = VideoDetailVO.from(video);
        vo.setRelatedTags(splitTags(video.getRelatedTags()));
        vo.setRelatedAnime(parseAnimeIds(video.getRelatedAnime()));
        vo.setComments(commentService.listByTarget(TARGET_TYPE, video.getId()));
        return vo;
    }

    /** DTO -> 实体（新增与修改共用） */
    private void apply(Video video, VideoSaveDTO dto) {
        video.setTitle(dto.getTitle());
        video.setThumbnail(dto.getThumbnail());
        video.setAuthor(dto.getAuthor());
        video.setAuthorAvatar(dto.getAuthorAvatar());
        video.setAuthorRole(dto.getAuthorRole());
        if (dto.getAuthorLevel() != null) {
            video.setAuthorLevel(dto.getAuthorLevel());
        }
        video.setAuthorBio(dto.getAuthorBio());
        video.setDescription(dto.getDescription());
        video.setExternalLink(dto.getExternalLink());
        if (dto.getPublishDate() != null) {
            video.setPublishDate(dto.getPublishDate());
        }
        if (dto.getRelatedTags() != null) {
            video.setRelatedTags(String.join(",", dto.getRelatedTags()));
        }
        if (dto.getRelatedAnime() != null) {
            video.setRelatedAnime(toAnimeIdsJson(dto.getRelatedAnime()));
        }
    }

    /** "MOM,AMV,治愈" -> ["MOM", "AMV", "治愈"] */
    private List<String> splitTags(String csv) {
        if (!StringUtils.hasText(csv)) {
            return new ArrayList<>();
        }
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .toList();
    }

    /** "1,4" -> [VideoAnimeItemVO(1), VideoAnimeItemVO(4)]（只回填 id，其余字段待 anime 模块就绪后补） */
    private List<VideoAnimeItemVO> parseAnimeIds(String csv) {
        List<VideoAnimeItemVO> result = new ArrayList<>();
        if (!StringUtils.hasText(csv)) {
            return result;
        }
        for (String part : csv.split(",")) {
            String trimmed = part.trim();
            if (!StringUtils.hasText(trimmed)) {
                continue;
            }
            try {
                result.add(VideoAnimeItemVO.fromId(Long.valueOf(trimmed)));
            } catch (NumberFormatException ignored) {
                // 脏数据直接跳过，不影响详情返回
            }
        }
        return result;
    }

    /** [1, 4] -> "1,4" */
    private String toAnimeIdsJson(List<Long> animeIds) {
        return animeIds.stream()
                .filter(java.util.Objects::nonNull)
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse(null);
    }
}
