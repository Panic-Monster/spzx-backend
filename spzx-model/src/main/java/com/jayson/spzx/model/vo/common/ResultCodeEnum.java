package com.jayson.spzx.model.vo.common;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    LOGIN_ERROR(201, "用户名或者密码错误"),
    VALIDATECODE_ERROR(202, "验证码错误"),
    DATA_ERROR(204, "数据异常"),
    PARAMS_ERROR(205, "参数错误"),
    LOGIN_AUTH(208, "用户未登录"),
    USER_NAME_IS_EXISTS(209, "用户名已经存在"),

    ACCOUNT_STOP(216, "账号已停用"),
    NODE_ERROR(217, "该节点下有子节点，不可以删除"),
    STOCK_LESS(219, "库存不足"),

    SYSTEM_ERROR(501, "您的网络有问题请稍后重试"),
    OPERATION_ERROR(502, "操作失败"),

    ;

    private final Integer code;      // 业务状态码

    private final String message;    // 响应消息

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
