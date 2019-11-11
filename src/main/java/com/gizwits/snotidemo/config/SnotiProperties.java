package com.gizwits.snotidemo.config;

import com.gizwits.noti.noticlient.OhMyNotiClient;
import com.gizwits.noti.noticlient.bean.req.body.LoginReqCommandBody;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Jcxcc
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "gizwits.snoti")
public class SnotiProperties {

    public SnotiProperties() {
        this.port = 2017;
        this.host = "snoti.gizwits.com";
        this.automaticConfirmation = true;
        this.itemList = Collections.emptyList();
    }

    /**
     * snoti 服务器 host
     */
    private String host;

    /**
     * snoti 服务器 port
     */
    private Integer port;

    /**
     * 自动确认
     * 默认为true
     * <p>
     * 当true时,
     * 通过使用 {@link OhMyNotiClient#receiveMessage()} 方法接受消息时会自动ack
     * 当false时，
     * 通过使用 {@link OhMyNotiClient#receiveMessage()} 方法接受消息时需要手动调用
     * 注意, 如果此时没有手动回复ack. 当达到了 preFetch{@link LoginReqCommandBody#getPrefetchCount()}时,
     * snoti服务端不会再推送消息.
     */
    private Boolean automaticConfirmation;

    /**
     * 登陆信息
     */
    private List<Item> itemList;

    @Data
    @NoArgsConstructor
    public static class Item {

        /**
         * 机智云产品key
         */
        private String productKey;

        /**
         * 机智云snoti auth id
         */
        private String authId;

        /**
         * 机智云snoti auth secret
         */
        private String authSecret;

        /**
         * 机智云snoti sub key
         */
        private String subKey;
    }
}
