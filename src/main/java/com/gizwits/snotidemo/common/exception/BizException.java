package com.gizwits.snotidemo.common.exception;

import lombok.Getter;

/**
 * @author Jcxcc
 * @since 1.0
 */
@Getter
public class BizException extends RuntimeException {

    private Integer code;

    private String message;

    public BizException() {
        super();
    }

    public BizException(final Integer code, final String msg) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    public BizException(String message) {
        this(500, message);
    }

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
