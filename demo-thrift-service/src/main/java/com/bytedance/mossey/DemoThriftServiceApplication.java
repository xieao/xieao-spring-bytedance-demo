package com.bytedance.mossey;

import com.bytedance.mysql.mybatis.MybatisSessionFactoryAutoConfiguration;
import com.bytedance.thrift.server.EnableThriftServices;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableThriftServices(basePackages = {
    "com.bytedance.mossey.demo.thrift",
})
@SpringBootApplication(exclude = {MybatisSessionFactoryAutoConfiguration.class})
@MapperScan("com.bytedance.mossey.demo.dal.db.mapper")
public class DemoThriftServiceApplication {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(DemoThriftServiceApplication.class, args);
    }
}
