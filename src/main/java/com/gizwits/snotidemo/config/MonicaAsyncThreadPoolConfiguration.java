package com.gizwits.snotidemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 异步线程池配置
 *
 * @author Jcxcc
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class MonicaAsyncThreadPoolConfiguration implements AsyncConfigurer {

    @Value("${monica.threadPool.maxSize:4}")
    private Integer maxSize;

    @Value("${monica.threadPool.coreSize:2}")
    private Integer coreSize;

    @Bean
    @Primary
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(2000);
        executor.setKeepAliveSeconds(60 * 2);
        executor.setThreadNamePrefix("Monica-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("执行异步任务出错 ===> 方法名称[{}], 参数列表[{}]", method.getName(), Arrays.stream(objects).map(Objects::toString).collect(Collectors.joining(",")));
            throwable.printStackTrace();
        };
    }
}
