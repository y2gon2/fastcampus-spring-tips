package dev.be.feign.controller;

import dev.be.feign.common.dto.BaseResponseInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

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
    }

}
