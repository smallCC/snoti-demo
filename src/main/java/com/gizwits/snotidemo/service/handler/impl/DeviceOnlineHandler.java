package com.gizwits.snotidemo.service.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.noti.noticlient.bean.resp.NotiRespPushEvents;
import com.gizwits.noti.noticlient.bean.resp.body.OnLineEventBody;
import com.gizwits.noti.noticlient.util.CommandUtils;
import com.gizwits.snotidemo.service.handler.PushEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 设备上线处理器
 *
 * @author Jcxcc
 * @since 1.0.0
 */
@Slf4j
@Component
public class DeviceOnlineHandler implements PushEventHandler {

    @Override
    public String getEventTypeCode() {
        return NotiRespPushEvents.DEVICE_ONLINE.getCode();
    }

    @Override
    public void handle(JSONObject message) {
        OnLineEventBody eventBody = CommandUtils.parsePushEvent(message, OnLineEventBody.class);
        final String
                productKey = eventBody.getProductKey(),
                mac = eventBody.getMac(),
                did = eventBody.getDid();
        //设备上线时间
        final Long createdAt = eventBody.getCreatedAt();

        //todo 添加对应当业务逻辑

        log.info("设备上线. productKey[{}] mac[{}] did[{}] createdAt[{}]", productKey, mac, did, createdAt);
    }
}
