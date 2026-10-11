package com.example.acgdata.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 登录用户上下文工具。
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /** 未登录返回 null */
    public static LoginUser getLoginUserOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoginUser loginUser) {
            return loginUser;
        }
        return null;
    }

    /** 未登录抛 BusinessException(401)，由全局异常处理器转成统一响应体 */
    public static LoginUser getLoginUser() {
        LoginUser loginUser = getLoginUserOrNull();
        if (loginUser == null) {
            throw new com.example.acgdata.common.exception.BusinessException(
                    com.example.acgdata.common.ResultCode.UNAUTHORIZED);
        }
        return loginUser;
    }

    public static Long getUserId() {
        return getLoginUser().getId();
    }

    public static boolean isLogin() {
        return getLoginUserOrNull() != null;
    }

    public static boolean isAdmin() {
        LoginUser loginUser = getLoginUserOrNull();
        return loginUser != null && loginUser.isAdmin();
    }
}
