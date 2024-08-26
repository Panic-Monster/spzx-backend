package com.jayson.spzx.common.exception;

import com.jayson.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @author: Jayson_Y
 * @date: 2024/8/25
 * @project: spzx-parent
 */
@Data
public class BusinessException extends RuntimeException {
    // 错误状态码
    private Integer code;
    // 错误消息
    private String message;

    private ResultCodeEnum resultCodeEnum;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
    public BusinessException(ResultCodeEnum resultCodeEnum, String message) {
        super(message);
        this.code = resultCodeEnum.getCode();
    }
}
