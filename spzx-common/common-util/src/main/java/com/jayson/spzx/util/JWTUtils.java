package com.jayson.spzx.util;


import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.jayson.spzx.model.entity.system.SysUser;

import java.util.Map;

/**
 * @author: Jayson_Y
 * @date: 2024/8/24
 * @project: spzx-parent
 */
public class JWTUtils {

    // 盐值
    private static final String SECRET = "fujimao";
    // 12小时过期
    private static final Long expire = 43200000L;

    /**
     * 生成 token
     * @param sysUser 携带的数据
     * @return token字符串
     */
    public String generateToken(SysUser sysUser) {
        return JWT.create()
                .setPayload("id", sysUser.getId())
                .setPayload("username", sysUser.getUserName())
                .setPayload("exp", System.currentTimeMillis() + expire)
                .setKey(SECRET.getBytes())
                // 默认HS256
                .sign();
    }

    /**
     * 解析 token
     * @param jwt token字符串
     * @return 解析token是否合法
     */
    public Boolean parseJwt (String jwt) {
        // 默认HS256
        return JWT.of(jwt).setKey(SECRET.getBytes()).verify();
    }
}
