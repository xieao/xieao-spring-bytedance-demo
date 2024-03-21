package com.bytedance.mossey.demo.thrift;

import com.bytedance.thrift.Base;
import com.bytedance.thrift.example.ExampleRequest;
import com.bytedance.thrift.example.ExampleResponse;
import com.bytedance.thrift.example.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class ExampleServiceImplTest {

    private TTransport transport;
    private ExampleService.Client client;

    @Before
    public void setUp() throws TTransportException {
        transport = new TSocket("127.0.0.1", 9000);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new ExampleService.Client(protocol);
    }

    @Test
    public void testVisit() throws TException {
        ExampleRequest request = new ExampleRequest();
        request.setName("test_name");
        ExampleResponse resp = client.Visit(request);
        log.info("response: {}", resp);
    }
}