package dev.be.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// thread pool 생성 및 지정
@Configuration
public class AppConfig {

    @Bean(name = "defaultTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor defaultTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(200);
        executor.setMaxPoolSize(300);
        executor.setKeepAliveSeconds(10);
        executor.setMaxPoolSize(300); // 기본값 == max pool size

        return executor;
    }

    @Bean(name = "messagingTaskExecutor", destroyMethod = "shutdown")  // destoryMethod : thread pool 상 task 가 정리되지 않는 경우
    public ThreadPoolTaskExecutor messagingTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(200);
        executor.setMaxPoolSize(300);
        executor.setKeepAliveSeconds(10);

        return executor;
    }
}
