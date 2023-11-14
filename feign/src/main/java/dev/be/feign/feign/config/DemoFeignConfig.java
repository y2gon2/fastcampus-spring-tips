package dev.be.feign.feign.config;

import dev.be.feign.feign.decoder.DemoFeignErrorDecoder;
import dev.be.feign.feign.interceptor.DemoFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * `FeignClient 마다 다른 설정을 정의하기 위한 config class
 * */
@Configuration
public class DemoFeignConfig {

    @Bean
    public DemoFeignInterceptor feignInterceptor() {
        return DemoFeignInterceptor.of();
    }

    @Bean
    public DemoFeignErrorDecoder demoErrorDecoder() {
        return new DemoFeignErrorDecoder();
    }
}
