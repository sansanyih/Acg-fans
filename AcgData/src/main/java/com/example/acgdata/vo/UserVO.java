package com.example.acgdata.vo;

import com.example.acgdata.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户出参：字段名与格式对齐前端 `UserInfo`，且**永远不包含密码**。
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;

    private Integer articleCount;
    private Integer commentCount;
    private String bio;
    private Integer level;
    private Boolean isAdmin;

    public static UserVO from(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setEmail(user.getEmail());
        vo.setAvatar(user.getAvatar());
        vo.setRegisterDate(user.getRegisterDate());
        vo.setLastLogin(user.getLastLogin());
        vo.setArticleCount(user.getArticleCount());
        vo.setCommentCount(user.getCommentCount());
        vo.setBio(user.getBio());
        vo.setLevel(user.getLevel());
        vo.setIsAdmin(user.getIsAdmin());
        return vo;
    }
}
