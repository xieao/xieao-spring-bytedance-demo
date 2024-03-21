package com.bytedance.mossey.demo.service;

import com.bytedance.jet.base.scopedvalue.ScopedValueExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AsyncService3
 *
 * @author xieao.mossey
 * @version 2024/03/21 19:55
 **/
@Slf4j
@Service
public class AsyncService {
    private final ExecutorService executorService = ScopedValueExecutorService.wrap(Executors.newFixedThreadPool(1));

    @Async
    public void testLog() {
        log.info("async 线程");
//        executorService.submit(() -> {
//            log.info("message");
//        });
    }
}
