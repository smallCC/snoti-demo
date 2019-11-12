package com.gizwits.snotidemo.common.exception;

import lombok.Getter;

/**
 * @author Jcxcc
 * @since 1.0
 */
@Getter
public enum BaseErrorCode implements ErrorCode {

    PARAMS_ERROR(400, "参数错误"),
    NOT_LOGGED(401, "未登录"),
    ACCESS_DENIED(403, "无权限访问"),
    SERVER_IS_BUSY(500, "系统繁忙, 请稍后再试"),
    ;

    BaseErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;
    private final String message;
}
