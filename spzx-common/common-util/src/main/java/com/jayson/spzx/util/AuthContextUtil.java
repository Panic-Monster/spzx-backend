package com.jayson.spzx.util;

import com.jayson.spzx.model.entity.system.SysUser;

import java.util.HashMap;

/**
 * @author: Jayson_Y
 * @date: 2024/8/26
 * @project: spzx-parent
 */
public class AuthContextUtil {

    // 创建一个threadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    // 添加数据
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // 获取数据
    public static SysUser get() {
        return threadLocal.get();
    }
    // 删除数据
    public static void remove() {
        threadLocal.remove();
    }
}
