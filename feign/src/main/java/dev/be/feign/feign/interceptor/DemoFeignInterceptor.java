package dev.be.feign.feign.interceptor;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;


@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class DemoFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        // Get 요청일 경우
        if(template.method() == Request.HttpMethod.GET.name()) {
            log.info("[GET] [DemoFeignInterceptor] queries : " + template.queries());
            return;
        }

        // Post 요청일 경우
        String encodedReqeustBody = StringUtils.toEncodedString(template.body(), StandardCharsets.UTF_8);
        log.info("[POST] [DemoFeignInterceptor] requestBody : " + encodedReqeustBody);

        // 추가적으로 추후 필요한 logic 추가 (내부 값 mapping 하여 String 으로 풀기 위해서)

        String convertReqeustBody = encodedReqeustBody;
        template.body(convertReqeustBody);
    }
}
