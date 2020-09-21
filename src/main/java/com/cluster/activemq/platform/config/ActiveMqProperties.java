package com.cluster.activemq.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    /**
     * 服务类型 producer、consumer
     */
    private String serviceType;
    /**
     * mq类型 topic queue
     */
    private String mqType;
    /**
     * mq名称 自定义名称
     */
    private String mqName;

    private String cmdNo;

    private String cmdMsg;

    private int sleepTime;

    private boolean isOpenTestMode;
}
