package com.cluster.activemq.platform.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-05-26 20:07
 **/
@Configuration
@EnableConfigurationProperties(value = {ActiveMqProperties.class})
public class ActiveMqAutoProperties {

}
