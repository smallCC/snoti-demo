package com.gizwits.snotidemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Snoti demo application.
 *
 * @author Jcxcc
 * @since 1.0.0
 */
@EnableAsync
@SpringBootApplication
public class SnotiDemoApplication implements ApplicationRunner {

    @Value("${service.port:8080}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(SnotiDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(String.format("服务已启动 http://localhost:%d/swagger-ui.html", port));
    }
}
