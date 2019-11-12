package com.gizwits.snotidemo.common.exception;

/**
 * 错误码接口
 *
 * @author Jcxcc
 * @since 1.0
 */
public interface ErrorCode {

    /**
     * Gets code.
     *
     * @return the code
     */
    Integer getCode();

    /**
     * Gets message.
     *
     * @return the message
     */
    String getMessage();
}
