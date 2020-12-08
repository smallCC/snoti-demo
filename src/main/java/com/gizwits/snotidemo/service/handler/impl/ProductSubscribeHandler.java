package com.gizwits.snotidemo.service.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.noti.noticlient.bean.resp.NotiRespPushEvents;
import com.gizwits.noti.noticlient.bean.resp.body.SubscribeCallbackEventBody;
import com.gizwits.noti.noticlient.util.CommandUtils;
import com.gizwits.snotidemo.service.handler.PushEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 产品订阅
 *
 * @author Jcxcc
 * @since 1.0.0
 */
@Slf4j
@Component
public class ProductSubscribeHandler implements PushEventHandler {

    @Override
    public String getEventTypeCode() {
        return NotiRespPushEvents.SUBSCRIBE_CALLBACK.getCode();
    }

    @Override
    public void handle(JSONObject message) throws Exception {
        SubscribeCallbackEventBody eventBody = CommandUtils.parsePushEvent(message, SubscribeCallbackEventBody.class);
        boolean success = StringUtils.equals(eventBody.getMsg(), "ok");
        String productKeys = eventBody.getData().stream().map(SubscribeCallbackEventBody.DataBean::getProductKey).collect(Collectors.joining(","));
        log.info("订阅产品消息结束. result[{}]. productKey[{}]", success, productKeys);
    }
}
