package com.gizwits.snotidemo.common.web;

import com.gizwits.snotidemo.common.exception.BaseErrorCode;
import com.gizwits.snotidemo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * The type Global exception handler.
 *
 * @author Jcxcc
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * The constant VERIFY_ERROR_CODE.
     */
    private final static int VERIFY_ERROR_CODE = 201;

    /**
     * 运行时异常
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse runtimeExceptionHandler(RuntimeException ex) {
        ex.printStackTrace();
        return ApiResponse.prompt(BaseErrorCode.SERVER_IS_BUSY);
    }

    /**
     * Null pointer exception handler api response.
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse nullPointerExceptionHandler(NullPointerException ex) {
        ex.printStackTrace();
        return ApiResponse.prompt(BaseErrorCode.PARAMS_ERROR.getCode(), ex.getMessage());
    }

    /**
     * IO异常
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(IOException.class)
    public ApiResponse IOExceptionHandle(IOException ex) {
        ex.printStackTrace();
        log.error(String.format("IOException:%s", ex));
        return ApiResponse.prompt(BaseErrorCode.SERVER_IS_BUSY);
    }

    /**
     * SQL异常
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(SQLException.class)
    public ApiResponse SQLExceptionHandle(IOException ex) {
        log.error(String.format("SQL异常:%s", ex));
        ex.printStackTrace();
        return ApiResponse.prompt(BaseErrorCode.SERVER_IS_BUSY);
    }

    /**
     * 500错误
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public ApiResponse server500(RuntimeException ex) {
        log.error(String.format("500错误:%s", ex));
        ex.printStackTrace();
        return ApiResponse.prompt(BaseErrorCode.SERVER_IS_BUSY);
    }

    /**
     * 验证统一处理
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return
                ApiResponse.prompt(VERIFY_ERROR_CODE, ex.getBindingResult().getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(",")));
    }

    /**
     * 验证统一处理
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse ConstraintViolationExceptionHandler(BindException ex) {
        return
                ApiResponse.prompt(VERIFY_ERROR_CODE, ex.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(",")));
    }

    /**
     * 没有访问权限
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse accessDeniedException(AccessDeniedException ex) {
        ex.printStackTrace();
        return
                ApiResponse.prompt(BaseErrorCode.ACCESS_DENIED);
    }

    /**
     * monica异常处理
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(BizException.class)
    public ApiResponse monicaException(BizException ex) {
        ex.printStackTrace();
        log.warn("系统错误. [{}]", ex.getMessage());
        return
                ApiResponse.prompt(ex.getCode(), ex.getMessage());
    }

    /**
     * Illegal argument exception api response.
     *
     * @param ex the ex
     * @return the api response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse illegalArgumentException(IllegalArgumentException ex) {
        ex.printStackTrace();
        return
                ApiResponse.prompt(BaseErrorCode.PARAMS_ERROR.getCode(), ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return
                ApiResponse.prompt(BaseErrorCode.PARAMS_ERROR.getCode(), ex.getMessage());
    }
}

