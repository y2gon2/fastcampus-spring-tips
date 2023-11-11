package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncService {

    private final EmailService emailService;

    public void asyncCall_1() {
        log.info("[asyncCall_1] :: " + Thread.currentThread().getName());

        // 1. Bean 주입을 받아서 사용하는 async code
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }
    // $ curl localhost:8080/1
    // [asyncCall_1] :: http-nio-8080-exec-1
    // [sendMail] :: defaultTaskExecutor-1
    // [messagingTaskExecutor] :: messagingTaskExecutor-1
    //  -> 예상대로 각각 다른 thread 에서 작업이 실행되었음을 확인
    //  -> Bean 생성되면 Spring 에서 비동기로 동작할 수 있게 Sub Thread 에게 위임하여 작업 요청 시 sub thread 가 이를 실행함.

    public void asyncCall_2() {
        log.info("[asyncCall_2] :: " + Thread.currentThread().getName());

        // 2. service instance 를 직접 생성하여 사용
        EmailService emailService = new EmailService();
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }
    // curl localhost:8080/2
    // [asyncCall_2] :: http-nio-8080-exec-2
    // [sendMail] :: http-nio-8080-exec-2
    // [messagingTaskExecutor] :: http-nio-8080-exec-2
    // -> EmailService instance 가 모두 현재 service method thread 에서 실행됨.
    // -> 비동기 처리 X

    public void asyncCall_3() {
        log.info("[asyncCall_3] :: " + Thread.currentThread().getName());

        // 3. 내부 method 를 async 로 구현하여 사용
        sendMail();
    }
    // $ curl localhost:8080/3
    // [asyncCall_3] :: http-nio-8080-exec-3
    // [sendMail] :: http-nio-8080-exec-3
    // -> EmailService instance 가 모두 현재 service method thread 에서 실행됨.
    // -> 비동기 처리 X
    // -> 동일 class 내부에서 직접 method 를 구현하고 실행할 경우,
    //    proxy 가 개입 하지 않아 @Async 작동하지 않는다.
    //    참고 : document/ChatGPT - 비동기 실행 방식 및 AOP 에 대해.mhtml

    // 3. 내부 method 를 async 로 구현하여 사용
    @Async("defaultTaskExecutor")
    public void sendMail() {
        log.info("[sendMail] :: " + Thread.currentThread().getName());
    }
}

