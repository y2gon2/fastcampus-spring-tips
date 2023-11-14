package dev.be.feign.controller;

import dev.be.feign.common.dto.BaseResponseInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/target_server")
public class TargetController {

    @GetMapping("/get")
    public BaseResponseInfo demoGet(
            @RequestHeader("CustomHeaderName") String header,
            @RequestParam("name") String name,
            @RequestParam("age") Long age
    ) {
        return BaseResponseInfo
                .builder()
                .header(header)
                .name(name)
                .age(age)
                .build();

        // Client method 에서는 BaseResponseInfo 를 ResponseEntity 로 wrapping 해서 받음.
    }

    @PostMapping("/post")
    public BaseResponseInfo demoPost(
            @RequestHeader("CustomHeaderName") String header,
            @RequestBody BaseResponseInfo body
    ) {
        return body;
    }

    @GetMapping("/error")
    public ResponseEntity<BaseResponseInfo> demoErrorDecoder() {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
