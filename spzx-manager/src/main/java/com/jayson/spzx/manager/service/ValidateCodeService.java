package com.jayson.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jayson.spzx.model.vo.system.ValidateCodeVo;

/**
 * @author: Jayson_Y
 * @date: 2024/8/25
 * @project: spzx-parent
 */
public interface ValidateCodeService {

    public ValidateCodeVo generateValidateCode();
}
