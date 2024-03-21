package com.bytedance.mossey.demo.http.config;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * 该切面实现了两个功能
 * 1. 设置businessId的threadLocal
 * 2. 实现logId的异步任务透传
 *  a. 实现原理
 *     logId是日志框架在生成日志message的时候,从mdc的threadLocal中读出,然后设置在对应属性值中.
 *     但是默认的线程池不支持threadLocal传递,在引入ttl后,我们支持了线程池传递threadLocal,但是需要将对应的threadLocal注册到Ttl
 *     此处,会在全部的controller入口处,将MDC的threadLocal注册到Ttl,后续的异步任务中都可以读到对应的logid
 *
 * @author xieao.mossey
 * @version 2024/03/21 20:55
 **/
@Slf4j
@Aspect
@Component
public class TraceInfoAspect {
    @Pointcut("execution(public * com.bytedance.mossey.demo.http.controllers.AsyncController.*(..))" )
    public void requestAspect(){}

    @Around(value = "requestAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("我进来了");
        List<ThreadLocal<?>> threadLocals = getMDCThreadLocal();
        try {
            // 注册mdc thread local(logId)
            threadLocals.forEach(TransmittableThreadLocal.Transmitter::registerThreadLocalWithShadowCopier);
            return joinPoint.proceed();
        } finally {
            threadLocals.forEach(TransmittableThreadLocal.Transmitter::unregisterThreadLocal);
        }
    }

    private List<ThreadLocal<?>> getMDCThreadLocal() {
        try {
            MDCAdapter mdcAdapter = MDC.getMDCAdapter();
            if (mdcAdapter instanceof LogbackMDCAdapter) {
                LogbackMDCAdapter logbackMDCAdapter = (LogbackMDCAdapter) mdcAdapter;
                return Arrays.stream(LogbackMDCAdapter.class.getDeclaredFields())
                    .filter(item -> item.getType() == ThreadLocal.class)
                    .map(item -> {
                        try {
                            item.setAccessible(true);
                            return (ThreadLocal<?>) item.get(logbackMDCAdapter);
                        } catch (IllegalAccessException e) {
                            log.error("get mdc adapter error", e);
                            return null;
                        }
                    }).filter(Objects::nonNull).collect(toList());
            }
        }catch (Exception e) {
            log.error("get mdc adapter error", e);
        }
        return Collections.emptyList();
    }
}
