package com.example.acgdata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 动态删除结果：对齐旧接口 `DELETE /api/feed/delete/{id}` 的返回结构
 * {@code { "success": true, "message": "删除成功" }}（success 是布尔值）。
 *
 * <p>删除失败（动态不存在）时抛 BusinessException(DATA_NOT_FOUND)，由全局异常处理器兜底。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success;

    private String message;

    public static FeedDeleteVO removed(String message) {
        return new FeedDeleteVO(Boolean.TRUE, message);
    }
}
