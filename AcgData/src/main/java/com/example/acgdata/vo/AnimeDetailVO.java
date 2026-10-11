package com.example.acgdata.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 番剧详情出参：在 {@link AnimeItemVO} 的基础上补齐前端 `AnimeDetail` 的字段。
 *
 * <p>列表字段（id / title / chineseTitle / image / description / tags /
 * episodes / startDate / lastEdit）直接继承，保证详情与列表完全同构。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnimeDetailVO extends AnimeItemVO {

    private static final long serialVersionUID = 1L;

    private String introduction;

    private String studio;

    private String broadcastDay;

    private String originalWork;

    private String director;

    private String script;

    private String storyboard;

    private String performance;

    private String music;

    private String characterDesign;

    private String seriesComposition;

    private String artDirector;

    private String colorDesign;

    private String chiefAnimationDirector;

    /** 声优 / 角色（嵌套数据，随详情一次返回） */
    private List<CharacterVO> characters;

    private String officialWebsite;

    /** 观影记录 / 评分 */
    private List<ViewingRecordVO> viewingRecords;

    /** 评论（来自共享 CommentService） */
    private List<CommentVO> comments;
}
