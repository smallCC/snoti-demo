package com.gizwits.snotidemo.service.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.noti.noticlient.bean.resp.NotiRespPushEvents;
import com.gizwits.noti.noticlient.bean.resp.body.GpsKvEventBody;
import com.gizwits.noti.noticlient.util.CommandUtils;
import com.gizwits.snotidemo.service.handler.PushEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 设备GPS信息处理器
 *
 * @author Jcxcc
 * @since 1.0.0
 */
@Slf4j
@Component
public class DeviceGpsKvHandler implements PushEventHandler {

    @Override
    public String getEventTypeCode() {
        return NotiRespPushEvents.GPS_KV.getCode();
    }

    @Override
    public void handle(JSONObject message) {
        GpsKvEventBody eventBody = CommandUtils.parsePushEvent(message, GpsKvEventBody.class);
        GpsKvEventBody.GpsKvData gps = eventBody.getData();
        Float latitude = gps.getLatitude();
        Float longitude = gps.getLongitude();
        final String
                productKey = eventBody.getProductKey(),
                mac = eventBody.getMac(),
                did = eventBody.getDid();

        //todo 添加对应当业务逻辑
        log.info("设备上报 GPS 信息. productKey[{}] mac[{}] did[{}] latitude[{}] longitude[{}]", productKey, mac, did, latitude, longitude);
    }
}
