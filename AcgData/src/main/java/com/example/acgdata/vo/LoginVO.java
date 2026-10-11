package com.example.acgdata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录 / 注册返回：JWT + 用户信息。
 */
@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** JWT，前端放在 Authorization: Bearer {token} */
    private String token;

    private String tokenType = "Bearer";

    /** 有效期（秒） */
    private long expiresIn;

    private UserVO user;

    public static LoginVO of(String token, long expiresIn, UserVO user) {
        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setExpiresIn(expiresIn);
        vo.setUser(user);
        return vo;
    }
}
