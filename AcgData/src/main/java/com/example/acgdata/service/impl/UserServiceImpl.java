package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.LoginDTO;
import com.example.acgdata.dto.RegisterDTO;
import com.example.acgdata.entity.User;
import com.example.acgdata.mapper.UserMapper;
import com.example.acgdata.security.JwtUtils;
import com.example.acgdata.service.UserService;
import com.example.acgdata.vo.LoginVO;
import com.example.acgdata.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现：密码 BCrypt 加盐存储，登录成功后签发 JWT。
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO login(LoginDTO dto) {
        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, dto.getUsername()), false);
        // 用户不存在与密码错误返回同一句提示，避免被用来探测用户名是否存在
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.LOGIN_FAILED);
        }

        User update = new User();
        update.setId(user.getId());
        update.setLastLogin(LocalDateTime.now());
        updateById(update);
        user.setLastLogin(update.getLastLogin());

        return buildLoginVO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO register(RegisterDTO dto) {
        boolean usernameExists = exists(Wrappers.<User>lambdaQuery().eq(User::getUsername, dto.getUsername()));
        if (usernameExists) {
            throw new BusinessException(ResultCode.USERNAME_EXISTS);
        }
        boolean emailExists = exists(Wrappers.<User>lambdaQuery().eq(User::getEmail, dto.getEmail()));
        if (emailExists) {
            throw new BusinessException(ResultCode.EMAIL_EXISTS);
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setAvatar(dto.getAvatar() == null ? "https://example.com/default-avatar.jpg" : dto.getAvatar());
        user.setRegisterDate(LocalDate.now());
        user.setLastLogin(LocalDateTime.now());
        user.setArticleCount(0);
        user.setCommentCount(0);
        user.setBio("这个人很懒，什么都没写~");
        user.setLevel(1);
        user.setIsAdmin(false);
        save(user);

        return buildLoginVO(user);
    }

    @Override
    public UserVO getVO(Long id) {
        return UserVO.from(getByIdOrThrow(id));
    }

    @Override
    public User getByIdOrThrow(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public List<UserVO> listAll() {
        return list(Wrappers.<User>lambdaQuery().orderByAsc(User::getId))
                .stream()
                .map(UserVO::from)
                .toList();
    }

    private LoginVO buildLoginVO(User user) {
        String token = jwtUtils.generateToken(user.getId(), user.getUsername(), Boolean.TRUE.equals(user.getIsAdmin()));
        return LoginVO.of(token, jwtUtils.getExpireSeconds(), UserVO.from(user));
    }
}
