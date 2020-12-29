package com.gizwits.snotidemo.service;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.noti.noticlient.OhMyNotiClient;
import com.gizwits.noti.noticlient.OhMyNotiClientImpl;
import com.gizwits.noti.noticlient.bean.Credential;
import com.gizwits.noti.noticlient.bean.req.NotiReqPushEvents;
import com.gizwits.noti.noticlient.config.SnotiCallback;
import com.gizwits.noti.noticlient.config.SnotiConfig;
import com.gizwits.snotidemo.config.SnotiProperties;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Snoti Bootstrap 服务
 * 应用启动时会自动引导Snoti服务
 *
 * @author Jcxcc
 * @since 1.0
 */
@Slf4j
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SnotiBootstrapService implements CommandLineRunner {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private SnotiProperties snotiProperties;

    @Autowired
    private PushEventRouter pushEventRouter;

    private OhMyNotiClient client;

    @Override
    public void run(String... args) throws Exception {
        startSnotiClient();
    }

    /**
     * Ctrl boolean.
     *
     * @param productKey the product key
     * @param mac        the mac
     * @param did        the did
     * @param attrs      the attrs
     * @return the boolean
     */
    public boolean ctrl(String productKey, String mac, String did, Map<String, Object> attrs) {
        log.info("发起设备控制. productKey[{}] mac[{}] did[{}] attrs[{}]", productKey, mac, did, attrs);
        return this.client.control(productKey, mac, did, attrs);
    }

    private void startSnotiClient() {
        log.info("开始启动snoti客户端...");

        client =
                new OhMyNotiClientImpl()
                        .setCredentials(getSnotiLoginCredential())
                        .setCallback(getSnotiCallback())
                        .setSnotiConfig(getSnotiConfig());

        //启动client
        client.doStart();

        //开始接收并分发消息
        taskExecutor.execute(() -> {
            for (; ; ) {
                JSONObject message = client.receiveMessage();
                pushEventRouter.dispatch(client, message);
            }
        });

        log.info("启动snoti客户端完成");
    }

    private SnotiConfig getSnotiConfig() {
        return new SnotiConfig()
                .setAutomaticConfirmation(snotiProperties.getAutomaticConfirmation())
                .setWithMetrics(true)
                .setEnableCheckNoData(true)
                .setNoDataWarningMinutes(1)
                .setHost(snotiProperties.getHost())
                .setPort(snotiProperties.getPort());
    }

    private SnotiCallback getSnotiCallback() {
        return new SnotiCallback() {

            @Override
            public void loginFailed(String errorMessage) {
                log.warn("snoti登录失败, 请检查登录参数是否有效!!! errorMsg\n {}", errorMessage);
                //TODO 通知
            }

            @Override
            public void disconnected() {
                log.warn("snoti客户端连接断开, 即将尝试重连...");
                //TODO 通知
            }
        };
    }

    private List<Credential> getSnotiLoginCredential() {
        List<SnotiProperties.Item> itemList = snotiProperties.getItemList();
        Preconditions.checkArgument(!CollectionUtils.isEmpty(itemList), "未配置snoti登陆信息, snoti初始化失败");
        return itemList.stream()
                .map(it ->
                        Credential.builder()
                                .events(Arrays.asList(NotiReqPushEvents.values()))
                                .subkey(it.getSubKey())
                                .authId(it.getAuthId())
                                .authSecret(it.getAuthSecret())
                                .productKey(it.getProductKey())
                                .build())
                .collect(Collectors.toList());
    }
}
