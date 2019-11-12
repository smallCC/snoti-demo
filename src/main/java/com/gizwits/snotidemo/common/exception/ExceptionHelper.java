package com.gizwits.snotidemo.common.exception;

/**
 * The type Exception helper.
 *
 * @author Jcxcc
 * @since 1.0
 */
public final class ExceptionHelper {

    //不允许创建
    private ExceptionHelper() {
    }

    /**
     * Throw monica exception.
     */
    public static void throwMonicaException() {
        throw new BizException();
    }

    /**
     * Throw monica exception.
     *
     * @param errorMessage the error message
     */
    public static void throwMonicaException(String errorMessage) {
        throw new BizException(errorMessage);
    }

    /**
     * Throw monica exception.
     *
     * @param errorCode the error code
     */
    public static void throwMonicaException(ErrorCode errorCode) {
        throw new BizException(errorCode);
    }
}
