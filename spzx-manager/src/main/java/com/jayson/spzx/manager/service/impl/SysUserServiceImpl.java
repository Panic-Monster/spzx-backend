package com.jayson.spzx.manager.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayson.spzx.common.exception.BusinessException;
import com.jayson.spzx.util.JWTUtils;
import com.jayson.spzx.manager.mapper.SysUserMapper;
import com.jayson.spzx.manager.service.SysUserService;
import com.jayson.spzx.model.dto.system.LoginDto;
import com.jayson.spzx.model.entity.system.SysUser;
import com.jayson.spzx.model.vo.common.ResultCodeEnum;
import com.jayson.spzx.model.vo.system.LoginVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

/**
* @author DELL
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-08-24 18:45:14
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录方法
     * @param loginDto 登录请求参数
     * @return loginVo 登录视图数据
     */
    @Override
    public LoginVo login(LoginDto loginDto) {
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();
        String redisCode = redisTemplate.opsForValue().get("user:validateCode" + key);
        // 比较 redis 中存的codeValue和输入的code是否一致（忽略大小写）
        if(CharSequenceUtil.isEmpty(redisCode) || !CharSequenceUtil.equalsIgnoreCase(captcha, redisCode)){
            throw new BusinessException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        // 一致则删除验证码
        redisTemplate.delete("user:validateCode" + key);
        // 根据用户名查询用户
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginDto.getUserName());
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        if(sysUser == null) {
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 验证密码是否正确
        String password = loginDto.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!md5Password.equals(sysUser.getPassword())) {
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 生成令牌存入redis中
        JWTUtils jwtUtils = new JWTUtils();
        String token = jwtUtils.generateToken(sysUser);
        redisTemplate.opsForValue().set("user:login"+token, JSON.toJSONString(sysUser), 12, TimeUnit.HOURS);
        // 返回
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    /**
     * 登录后获取用户信息
     * @param token 令牌
     * @return 用户信息
     */
    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    /**
     * 退出登录
     * @param token 令牌
     * @return null
     */
    @Override
    public Boolean logout(String token) {
        Boolean deleteTokenResult = redisTemplate.delete("user:login" + token);
        if(!deleteTokenResult) {
            throw new BusinessException(ResultCodeEnum.OPERATION_ERROR, "redis删除token失败");
        }
        return deleteTokenResult;
    }
}




