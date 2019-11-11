package com.gizwits.snotidemo.service.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.noti.noticlient.bean.resp.NotiRespPushEvents;
import com.gizwits.noti.noticlient.bean.resp.body.StatusKvEventBody;
import com.gizwits.noti.noticlient.util.CommandUtils;
import com.gizwits.snotidemo.service.handler.PushEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 设备kv数据点处理器
 *
 * @author Jcxcc
 * @since 1.0.0
 */
@Slf4j
@Component
public class DeviceStatusKvHandler implements PushEventHandler {

    @Override
    public String getEventTypeCode() {
        return NotiRespPushEvents.DEVICE_STATUS_KV.getCode();
    }

    @Override
    public void handle(JSONObject message) {
        StatusKvEventBody eventBody = CommandUtils.parsePushEvent(message, StatusKvEventBody.class);
        final String
                productKey = eventBody.getProductKey(),
                mac = eventBody.getMac(),
                did = eventBody.getDid();
        //消息上报时间
        final Long createdAt = eventBody.getCreatedAt();
        //数据点 kv
        JSONObject attrs = eventBody.getData();

        //todo 添加对应当业务逻辑

        log.info("设备上报状态. productKey[{}] mac[{}] did[{}] attrs[{}] createdAt[{}]", productKey, mac, did, attrs, createdAt);
    }
}
