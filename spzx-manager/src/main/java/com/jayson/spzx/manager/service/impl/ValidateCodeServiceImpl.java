package com.jayson.spzx.manager.service.impl;

import cn.hutool.Hutool;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import com.jayson.spzx.manager.service.ValidateCodeService;
import com.jayson.spzx.model.vo.system.ValidateCodeVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: Jayson_Y
 * @date: 2024/8/25
 * @project: spzx-parent
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        // 1、hutool生成图片验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 8);
        String codeValue = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();
        // 2、验证码存入redis中、设置过期时间
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:validateCode" + key, codeValue, 3, TimeUnit.MINUTES);
        // 3、返回ValidateCodeVo
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
