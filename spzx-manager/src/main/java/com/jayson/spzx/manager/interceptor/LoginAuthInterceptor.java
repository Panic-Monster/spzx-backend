package com.jayson.spzx.manager.interceptor;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.jayson.spzx.common.exception.BusinessException;
import com.jayson.spzx.model.entity.system.SysUser;
import com.jayson.spzx.model.vo.common.Result;
import com.jayson.spzx.model.vo.common.ResultCodeEnum;
import com.jayson.spzx.util.AuthContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author: Jayson_Y
 * @date: 2024/8/26
 * @project: spzx-parent
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1 获取请求方式
        //如果请求方式是options，预检请求，直接放行
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }

        //2 获取token
        String token = request.getHeader("token");

        //3 token为空，返回错误提示
        if(CharSequenceUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }

        //4 token不为空，查redis
        String userInfoString = redisTemplate.opsForValue().get("user:login" + token);

        //5 reids为空，返回错误提示
        if(CharSequenceUtil.isEmpty(userInfoString)){
            responseNoLoginInfo(response);
            return false;
        }

        //6 查到数据就把用户信息放到ThreadLocal中
        SysUser sysuser = JSON.parseObject(userInfoString, SysUser.class);
        AuthContextUtil.set(sysuser);

        //7 把redis用户数据更新过期时间
        redisTemplate.expire("user:login" + token, 30, TimeUnit.MINUTES);

        //8 放行
        return true;
    }

    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.remove();  // 移除threadLocal中的数据
    }
}
