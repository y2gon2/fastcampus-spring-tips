package dev.be.feign.service;

import dev.be.feign.common.dto.BaseRequestInfo;
import dev.be.feign.common.dto.BaseResponseInfo;
import dev.be.feign.feign.client.DemoFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemoService {

    private final DemoFeignClient demoFeignClient;

    public String get() {
        ResponseEntity<BaseResponseInfo> response = demoFeignClient.callGet(
                "CustomHeader",
                "CustomName",
                1L
        );

        log.info("Name : " + response.getBody().getName());
        log.info("Age : " + response.getBody().getAge());
        log.info("Header : " + response.getHeaders());

        return "Success Fiegn";
    }

    public String post() {
        BaseRequestInfo baseRequestInfo = BaseRequestInfo
                .builder()
                .name("customName")
                .age(2L)
                .build();

        ResponseEntity<BaseResponseInfo> response = demoFeignClient.callPost(
                "CustomHeader",
                baseRequestInfo
        );

        log.info("Name : " + response.getBody().getName());
        log.info("Age : " + response.getBody().getAge());
        log.info("Header : " + response.getHeaders());

        return "Success Fiegn POST";
    }
}
