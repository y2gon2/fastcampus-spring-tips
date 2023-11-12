package dev.be.feign.config;

import dev.be.feign.feign.logger.FeignCustomLogger;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 전역적 설정을 정의하기 위한 config class
 * */
@Configuration
public class FeignConfig {

    // logger 의 경우 일반적으로 전역적 설정에 반영
    @Bean
    public Logger feignLogger() {
        return new FeignCustomLogger();
    }
}
