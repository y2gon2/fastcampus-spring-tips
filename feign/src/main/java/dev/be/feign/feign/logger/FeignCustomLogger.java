package dev.be.feign.feign.logger;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static feign.Util.*;

@Slf4j
@RequiredArgsConstructor
public class FeignCustomLogger extends Logger {
    private static final int DEFAULT_SLOW_API_TIME = 3_000;
    private static final String SLOW_API_NOTICE = "Slow API";

    @Override
    protected void log(
            String configKey,
            String format,
            Object... args
    ) {
        // log 를 어떤 형식으로 남길지 정해준다.
        log.info(String.format(methodTag(configKey) + format, args));
    }

    // request 만 handling 가능
    @Override
    protected void logRequest(
            String configKey,
            Level logLevel,
            Request request
    ) {
        log.info("[logRequest] : " + request);
    }
    //  출력 결과
    //    [logRequest] : POST http://localhost:8080/target_server/post HTTP/1.1
    //    Content-Length: 29
    //    Content-Type: application/json
    //    CustomHeaderName: CustomHeader
    //
    //    {"name":"customName","age":2}

    // request & response handling 가능
    @Override
    protected Response logAndRebufferResponse(
            String configKey,
            Level logLevel,
            Response response,
            long elapsedTime
    ) throws IOException {
        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
        String reason = response.reason() != null
                && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason() : "";

        int status = response.status();
        log(
                configKey,
                "<-- %s %s%s (%sms)",
                protocolVersion,
                status,
                reason,
                elapsedTime // 실무에서 굉장히 중요한 요소 -> 의도치 안게 시간이 오래 걸릴 경우 timeout 이 발생할 수도 있음.
        );
        // [DemoFeignClient#callPost] <-- HTTP/1.1 200 (32ms)

        // logLevel.ordinal() : enum 으로 정의된 level 순서 값을 가져옴
        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

            for (String field : response.headers().keySet()) {
                if (shouldLogResponseHeader(field)) {
                    for (String value : valuesOrEmpty(response.headers(), field)) {
                        log(configKey, "%s: %s", field, value);
                    }
                }
            }
            // header 내 모든 값들을 반복문을 이용하여 출력

            int bodyLength = 0;
            if (response.body() != null && !(status == 204 || status == 205)) {
                // HTTP 204 No Content "...response MUST NOT include a message-body"
                // HTTP 205 Reset Content "...response MUST NOT include an entity"
                if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                    log(configKey, "");  // CRLF (Carriage Return Line Feed)
                }
                byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                bodyLength = bodyData.length;
                if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
                    log(configKey, "%s", decodeOrDefault(bodyData, UTF_8, "Binary data"));
                }

                if (elapsedTime > DEFAULT_SLOW_API_TIME) {
                    log(configKey, "[%s] elapsedTime : %s", SLOW_API_NOTICE, elapsedTime);
                }

                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);

                return response.toBuilder().body(bodyData).build();
            } else {
                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
            }
        }
        return response;
    }
    // 출력
    // [DemoFeignClient#callPost] <-- HTTP/1.1 200 (32ms)
    // [DemoFeignClient#callPost] connection: keep-alive
    // [DemoFeignClient#callPost] content-type: application/json
    // [DemoFeignClient#callPost] date: Sun, 12 Nov 2023 04:55:15 GMT
    // [DemoFeignClient#callPost] keep-alive: timeout=60
    // [DemoFeignClient#callPost] transfer-encoding: chunked
    // [DemoFeignClient#callPost] <--- END HTTP (29-byte body)
}
