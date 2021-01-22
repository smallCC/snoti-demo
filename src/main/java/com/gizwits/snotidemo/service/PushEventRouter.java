package com.gizwits.snotidemo.service;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.noti.noticlient.OhMyNotiClient;
import com.gizwits.noti.noticlient.bean.resp.NotiRespPushEvents;
import com.gizwits.noti.noticlient.util.CommandUtils;
import com.gizwits.snotidemo.config.SnotiProperties;
import com.gizwits.snotidemo.service.handler.PushEventHandler;
import com.gizwits.snotidemo.service.handler.impl.DefaultPushEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 根据推送的消息进行一个分发到具体的事件处理器
 *
 * @author Jcxcc
 * @since 1.0.0
 */
@Slf4j
@Service
public class PushEventRouter implements ApplicationContextAware {

    private Map<String, List<PushEventHandler>> routerMap;

    @Autowired
    private DefaultPushEventHandler defaultPushEventHandler;

    @Autowired
    private SnotiProperties snotiProperties;

    /**
     * 分发消息
     * <p>
     * 1. 异步处理
     * 2. 过滤消息
     * 3. 根据消息类型分发消息并处理, 如果不匹配则使用默认处理器进行处理{@link DefaultPushEventHandler#fire(JSONObject)}
     *
     * @param client  the client
     * @param message the json
     */
    @Async
    public void dispatch(OhMyNotiClient client, JSONObject message) {
        // 无法匹配时返回 invalid
        String pushEventCode = CommandUtils.getPushEventCode(message);

        this.routerMap.getOrDefault(pushEventCode, Collections.singletonList(defaultPushEventHandler))
                .forEach(it -> it.fire(message));

        /**
         * 当{@link SnotiProperties#getAutomaticConfirmation()} 为false时,
         * 代表需要收到回复
         */
        if (!snotiProperties.getAutomaticConfirmation()) {
            client.confirm(message);
        }
    }

    /**
     * 查找  {@link NotiRespPushEvents} 对应的事件处理器
     * <p>
     * {@link DefaultPushEventHandler} 默认事件处理器
     * <p>
     * {@link com.gizwits.snotidemo.service.handler.impl.DeviceStatusKvHandler} 设备状态 kv 值处理器
     * <p>
     * {@link com.gizwits.snotidemo.service.handler.impl.DeviceOnlineHandler} 设备上线处理器
     * <p>
     * {@link com.gizwits.snotidemo.service.handler.impl.DeviceOfflineHandler} 设备离线处理器
     * <p>
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initRouterMap(applicationContext);
    }

    private void initRouterMap(ApplicationContext applicationContext) {
        log.info("开始初始化消息处理器路由表...");

        this.routerMap = applicationContext.getBeansOfType(PushEventHandler.class).values().stream()
                //过滤有效的推送事件处理器
                .filter(it -> StringUtils.isNotBlank(it.getEventTypeCode()))
                //数值越大越优先
                .sorted(Comparator.comparing(PushEventHandler::getPriority).reversed())
                .collect(Collectors.groupingBy(PushEventHandler::getEventTypeCode));
        log.info("初始化消息处理器路由表成功.");
    }
}
