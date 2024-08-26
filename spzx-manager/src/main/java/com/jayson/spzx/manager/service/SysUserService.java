package com.jayson.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jayson.spzx.model.dto.system.LoginDto;
import com.jayson.spzx.model.entity.system.SysUser;
import com.jayson.spzx.model.vo.system.LoginVo;

/**
* @author DELL
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2024-08-24 18:45:14
*/
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录方法
     * @param loginDto 登录请求参数
     * @return 登录视图数据
     */
    LoginVo login (LoginDto loginDto);

    /**
     * 登录后获取用户信息
     * @param token 令牌
     * @return 用户信息
     */
    SysUser getUserInfo (String token);

    /**
     * 退出登录
     * @param token 令牌
     * @return 是否成功退出
     */
    Boolean logout(String token);
}
