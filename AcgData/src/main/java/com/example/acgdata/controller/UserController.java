package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.LoginDTO;
import com.example.acgdata.dto.RegisterDTO;
import com.example.acgdata.service.UserService;
import com.example.acgdata.vo.LoginVO;
import com.example.acgdata.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户接口：登录 / 注册签发 JWT，查询接口不返回密码。
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO dto) {
        return Result.ok("登录成功", userService.login(dto));
    }

    @PostMapping("/register")
    public Result<LoginVO> register(@Valid @RequestBody RegisterDTO dto) {
        return Result.ok("注册成功", userService.register(dto));
    }

    @GetMapping("/get/{id}")
    public Result<UserVO> getUserById(@PathVariable Long id) {
        return Result.ok(userService.getVO(id));
    }

    /** 用户列表，仅管理员可访问（权限规则见 SecurityConfig） */
    @GetMapping("/get/all")
    public Result<List<UserVO>> getAllUsers() {
        return Result.ok(userService.listAll());
    }
}
