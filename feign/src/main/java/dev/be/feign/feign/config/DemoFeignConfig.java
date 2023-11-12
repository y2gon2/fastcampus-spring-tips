package dev.be.feign.feign.config;

import dev.be.feign.feign.interceptor.DemoFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * `feign` floder 하위 내부에 존재하는 FeignClient 를 위한 설정 조건을
 *  정의하기 위한 config class
 * */
@Configuration
public class DemoFeignConfig {

    @Bean
    public DemoFeignInterceptor feignInterceptor() {
        return DemoFeignInterceptor.of();
    }
}
