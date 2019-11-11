package com.gizwits.snotidemo.service.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.noti.noticlient.bean.resp.NotiRespPushEvents;
import com.gizwits.snotidemo.service.handler.PushEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 无效推送事件处理器
 * <p>
 * {@link NotiRespPushEvents}
 *
 * @author Jcxcc
 * @since 1.0.0
 */
@Slf4j
@Component
public class InvalidPushEventHandler implements PushEventHandler {

    @Override
    public String getEventTypeCode() {
        return NotiRespPushEvents.INVALID.getCode();
    }

    @Override
    public void handle(JSONObject message) {
        log.warn("推送事件不处理. 事件[{}]", message.toJSONString());
    }
}
