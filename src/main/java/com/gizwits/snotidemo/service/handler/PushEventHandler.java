package com.gizwits.snotidemo.service.handler;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.noti.noticlient.bean.resp.NotiRespPushEvents;
import com.gizwits.noti.noticlient.util.CommandUtils;

/**
 * 推送事件处理器
 *
 * @author Jcxcc
 * @since 1.0.0
 */
public interface PushEventHandler {

    /**
     * 获取优先级
     * <p>
     * 默认为0, 越大越优先
     *
     * @return the int
     */
    default int getPriority() {
        return 0;
    }

    /**
     * 获取事件类型
     *
     * @return the event type
     */
    default NotiRespPushEvents getEventType() {
        return CommandUtils.getResEvent(getEventTypeCode());
    }

    /**
     * 异常处理
     *
     * @return exception caught handler
     */
    default ExceptionCaughtHandler exceptionCaughtHandler() {
        return ExceptionCaughtHandler.identity();
    }

    /**
     * 执行
     *
     * @param message the message
     */
    default void fire(JSONObject message) {
        try {
            handle(message);
        } catch (Throwable throwable) {
            exceptionCaughtHandler().exceptionCaught(message, throwable);
        }
    }

    /**
     * 获取事件类型编码
     *
     * @return the string
     */
    String getEventTypeCode();

    /**
     * 处理消息
     *
     * @param message the message
     * @throws Exception the exception
     */
    void handle(JSONObject message) throws Exception;
}
