package com.bytedance.mossey.demo.http.controllers;

import com.bytedance.logging.agent.utils.MDCKeys;
import com.bytedance.mossey.demo.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AsyncController
 *
 * @author xieao.mossey
 * @version 2024/03/21 19:51
 **/
@Slf4j
@RestController
@RequestMapping("/api")
public class AsyncController {

    private final AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping(value = "/visit")
    public String visit(@RequestParam("msg") String msg) {
        log.info("线程池外：logId={}", MDC.get(MDCKeys.LOG_ID));
        asyncService.testLog();
        return "success";
    }
}
