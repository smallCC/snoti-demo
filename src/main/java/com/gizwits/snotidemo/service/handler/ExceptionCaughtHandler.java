package com.gizwits.snotidemo.service.handler;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常处理
 */
@FunctionalInterface
public interface ExceptionCaughtHandler {

    /**
     * Exception caught.
     *
     * @param message the message
     * @param cause   the cause
     */
    void exceptionCaught(JSONObject message, Throwable cause);

    static ExceptionCaughtHandler identity() {
        return new ExceptionCaughtHandler() {

            private Logger logger = LoggerFactory.getLogger("DefaultExceptionCaughtHandler");

            @Override
            public void exceptionCaught(JSONObject message, Throwable cause) {
                logger.warn("处理消息发生错误. message[{}] error[{}]", message.toJSONString(), cause.getMessage());
            }
        };
    }
}
