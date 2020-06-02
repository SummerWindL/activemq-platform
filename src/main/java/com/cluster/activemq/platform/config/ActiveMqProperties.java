package com.cluster.activemq.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-05-26 16:14
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.activemq")
public class ActiveMqProperties {
    private String brokerUrl;
    private String user;
    private String password;
}
