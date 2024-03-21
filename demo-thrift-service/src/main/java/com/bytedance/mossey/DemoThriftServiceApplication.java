package com.bytedance.mossey;

import com.bytedance.thrift.server.EnableThriftServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableThriftServices(basePackages = {
    "com.bytedance.mossey.demo.thrift",
})
@SpringBootApplication
public class DemoThriftServiceApplication {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(DemoThriftServiceApplication.class, args);
    }
}
