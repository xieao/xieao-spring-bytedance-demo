package com.bytedance.mossey.demo.thrift;

import com.bytedance.thrift.BaseResp;
import com.bytedance.thrift.example.ExampleRequest;
import com.bytedance.thrift.example.ExampleResponse;
import com.bytedance.thrift.example.ExampleService;
import com.bytedance.thrift.server.ThriftService;
import org.apache.thrift.TException;

@ThriftService
public class ExampleServiceImpl implements ExampleService.Iface {

    @Override
    public ExampleResponse Visit(ExampleRequest req) throws TException {
        BaseResp baseResp = new BaseResp("foo", 100);
        ExampleResponse response = new ExampleResponse();
        response.setBaseResp(baseResp);
        response.setResp(String.format("hello %s", req.getName()));

        return response;
    }
}
