package com.gizwits.snotidemo.common.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gizwits.snotidemo.common.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * The type Api response.
 *
 * @param <T> the type parameter
 * @author Jcxcc
 * @since 1.0
 */
@ApiModel
@Accessors(chain = true)
public class ApiResponse<T> {

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    @Getter
    @Setter
    @ApiModelProperty("状态码")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int status;

    @Getter
    @Setter
    @ApiModelProperty("消息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @Getter
    @Setter
    @ApiModelProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @Getter
    @JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("响应时间戳")
    private LocalDateTime timestamp;

    @JsonIgnore
    public boolean success() {
        return this.status == 200;
    }

    @JsonIgnore
    public boolean failure() {
        return !success();
    }

    /**
     * Ok api response.
     *
     * @return the api response
     */
    public static ApiResponse<String> ok() {
        ApiResponse<String> response = new ApiResponse<>();

        response.setStatus(200);
        response.setMessage("succeed");

        return response;
    }

    /**
     * Ok api response.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the api response
     */
    public static <T> ApiResponse<T> ok(T data) {
        return
                new ApiResponse<T>().setStatus(200).setMessage("请求成功").setData(data);
    }

    /**
     * Prompt api response.
     *
     * @param <T>  the type parameter
     * @param code the code
     * @param msg  the msg
     * @param data the data
     * @return the api response
     */
    public static <T> ApiResponse<T> prompt(int code, String msg, T data) {
        return
                new ApiResponse<T>().setStatus(code).setMessage(msg).setData(data);
    }

    /**
     * Prompt api response.
     *
     * @param code the code
     * @param msg  the data
     * @return the api response
     */
    public static ApiResponse prompt(int code, String msg) {
        return
                new ApiResponse().setStatus(code).setMessage(msg);
    }

    /**
     * Prompt api response.
     *
     * @param errorCode the error code
     * @return the api response
     */
    public static ApiResponse prompt(ErrorCode errorCode) {
        return
                new ApiResponse().setStatus(errorCode.getCode()).setMessage(errorCode.getMessage());
    }
}
