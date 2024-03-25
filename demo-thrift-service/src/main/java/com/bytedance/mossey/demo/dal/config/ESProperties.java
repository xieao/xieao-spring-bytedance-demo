package com.bytedance.mossey.demo.dal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * ESProperties ES配置信息
 *
 * @author xieao.mossey
 * @version 2024/03/25 16:13
 **/
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "elasticsearch")
public class ESProperties {
    String host;
    Integer port;
}
