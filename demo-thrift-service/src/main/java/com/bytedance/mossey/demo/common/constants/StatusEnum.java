package com.bytedance.mossey.demo.common.constants;

import lombok.Getter;

/**
 * StatusEnum 接口返回码
 *
 * @author xieao.mossey
 * @version 2024/03/18 16:00
 **/
@Getter
public enum StatusEnum {
    SUCCESS(200, "请求处理成功"),
    UNAUTHORIZED(401, "用户认证失败"),
    FORBIDDEN(403, "权限不足"),
    SERVICE_ERROR(500, "服务器去旅行了，请稍后重试"),
    PARAM_INVALID(1000, "无效的参数"),
    JSON_SYNTAX_INVALID(1001, "JSON异常"),
    ;

    public final Integer code;
    public final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
