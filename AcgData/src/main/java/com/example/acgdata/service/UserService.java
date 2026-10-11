package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.LoginDTO;
import com.example.acgdata.dto.RegisterDTO;
import com.example.acgdata.entity.User;
import com.example.acgdata.vo.LoginVO;
import com.example.acgdata.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {

    LoginVO login(LoginDTO dto);

    /** 注册成功后直接返回 token，前端无需再调一次登录 */
    LoginVO register(RegisterDTO dto);

    UserVO getVO(Long id);

    User getByIdOrThrow(Long id);

    List<UserVO> listAll();
}
