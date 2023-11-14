package dev.be.feign.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class DemoFeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    // decode 메소드는 FeignException 또는 사용자 정의 예외를 반환
    // 이 메소드에서는 Response 객체를 분석하여 오류의 원인을 파악하고 적절한 예외를 발생시킴.
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus httpStatus = HttpStatus.resolve(response.status());

        // 사용자가 정의한 error 에 대한 응답 처리
        if (httpStatus == httpStatus.NOT_FOUND) {
            System.out.println("[DemoFeignErrorDecoder] Http Status = " + httpStatus);
            throw new RuntimeException(String.format("[RuntimeException] Http Status is %s", httpStatus));
        }

        // Feign 에서 정의한 기본적인 error decoder 내용을 사용
        return errorDecoder.decode(methodKey, response);
    }
}
