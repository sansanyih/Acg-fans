package com.example.acgdata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色删除结果：对齐旧接口 `DELETE /api/character/delete/{id}` 的返回结构
 * {@code { "success": "true", "message": "角色删除成功" }}。
 *
 * <p>注意：这里的 {@code success} 在旧接口里是**字符串**（`String.valueOf(boolean)`），
 * 不是布尔值，所以类型保持 String，逐字兼容。
 * 删除失败（角色不存在）时抛 BusinessException(DATA_NOT_FOUND)，由全局异常处理器兜底。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String success;

    private String message;

    public static CharacterDeleteVO removed(String message) {
        return new CharacterDeleteVO(Boolean.TRUE.toString(), message);
    }
}
