package com.bytedance.mossey.demo.thrift;

import com.bytedance.logging.agent.utils.MDCKeys;
import com.bytedance.thrift.BaseResp;
import com.bytedance.thrift.example.ExampleRequest;
import com.bytedance.thrift.example.ExampleResponse;
import com.bytedance.thrift.example.ExampleService;
import com.bytedance.thrift.server.ThriftService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.slf4j.MDC;

@Slf4j
@ThriftService
public class ExampleServiceImpl implements ExampleService.Iface {

    @Override
    public ExampleResponse Visit(ExampleRequest req) throws TException {
        BaseResp baseResp = new BaseResp("foo", 100);
        ExampleResponse response = new ExampleResponse();
        response.setBaseResp(baseResp);
        response.setResp(String.format("hello %s", req.getName()));
        log.info("my name is {}, logId:{}", req.getName(), MDC.get(MDCKeys.LOG_ID));
        return response;
    }
}
