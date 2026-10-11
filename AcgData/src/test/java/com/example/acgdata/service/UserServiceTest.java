package com.example.acgdata.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.LoginDTO;
import com.example.acgdata.entity.User;
import com.example.acgdata.vo.LoginVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Service 层测试：覆盖 BCrypt 密码存储、JWT 签发、业务异常码。
 */
@SpringBootTest
@DisplayName("用户服务：登录签发 JWT + BCrypt 密码")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("账号密码正确：签发 token 并回填用户信息")
    void login_withCorrectPassword_shouldReturnToken() {
        LoginVO vo = userService.login(loginDTO("admin", "admin123"));

        assertThat(vo.getToken()).isNotBlank();
        assertThat(vo.getTokenType()).isEqualTo("Bearer");
        assertThat(vo.getExpiresIn()).isPositive();
        assertThat(vo.getUser().getUsername()).isEqualTo("admin");
        assertThat(vo.getUser().getIsAdmin()).isTrue();
    }

    @Test
    @DisplayName("密码错误：抛 LOGIN_FAILED，且不泄露用户是否存在")
    void login_withWrongPassword_shouldThrowLoginFailed() {
        assertThatThrownBy(() -> userService.login(loginDTO("admin", "wrong-password")))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getCode())
                .isEqualTo(ResultCode.LOGIN_FAILED.getCode());

        // 用户不存在时返回同一错误码
        assertThatThrownBy(() -> userService.login(loginDTO("no-such-user", "whatever")))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getCode())
                .isEqualTo(ResultCode.LOGIN_FAILED.getCode());
    }

    @Test
    @DisplayName("密码以 BCrypt 加盐存储，可用 matches 校验")
    void password_shouldBeStoredAsBCrypt() {
        User admin = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, "admin"), false);

        assertThat(admin).isNotNull();
        assertThat(admin.getPassword()).startsWith("$2a$");
        assertThat(admin.getPassword()).doesNotContain("admin123");
        assertThat(passwordEncoder.matches("admin123", admin.getPassword())).isTrue();
    }

    @Test
    @DisplayName("查询不存在的用户：抛 USER_NOT_FOUND")
    void getByIdOrThrow_withUnknownId_shouldThrow() {
        assertThatThrownBy(() -> userService.getByIdOrThrow(-1L))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getCode())
                .isEqualTo(ResultCode.USER_NOT_FOUND.getCode());
    }

    private LoginDTO loginDTO(String username, String password) {
        LoginDTO dto = new LoginDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        return dto;
    }
}
