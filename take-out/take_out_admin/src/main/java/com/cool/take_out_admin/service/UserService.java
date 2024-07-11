package com.cool.take_out_admin.service;

import com.cool.dto.UserLoginDTO;
import com.cool.entity.User;

public interface UserService {
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxlogin(UserLoginDTO userLoginDTO);
}
