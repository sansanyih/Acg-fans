package com.example.acgdata.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色点赞 / 踩的操作结果，逐字对齐旧接口：
 * <ul>
 *     <li>like 成功：{@code { "success": true, "likes": 12501 }}</li>
 *     <li>dislike 成功：{@code { "success": true, "dislikes": 121 }}</li>
 * </ul>
 * 用 {@code NON_NULL} 保证不把「用不上的那个字段」以 null 形式多输出。
 * 角色不存在时抛 BusinessException(DATA_NOT_FOUND)，不再返回 {@code success:false} 的软失败。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterLikeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success;

    private Integer likes;

    private Integer dislikes;

    private String message;
}
