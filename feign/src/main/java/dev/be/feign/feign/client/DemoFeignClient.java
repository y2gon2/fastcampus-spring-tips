package dev.be.feign.feign.client;

import dev.be.feign.common.dto.BaseRequestInfo;
import dev.be.feign.common.dto.BaseResponseInfo;
import dev.be.feign.feign.config.DemoFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "demo-client", // 해당 interface 을 가리키는 Key
        url = "${feign.url.prefix}", // client 가 요청을 보내는 target server url
        configuration = DemoFeignConfig.class
)
public interface DemoFeignClient {

    @GetMapping("/get") // -> http://localhost:8080/target_server/get
    ResponseEntity<BaseResponseInfo> callGet(
            @RequestHeader("CustomHeaderName") String customHeader,
            @RequestParam("name") String name,
            @RequestParam("age") Long age
            );

    @PostMapping("/post")
    ResponseEntity<BaseResponseInfo> callPost(
            @RequestHeader("CustomHeaderName") String customHeader,
            @RequestBody BaseRequestInfo baseRequestInfo
            );
}
