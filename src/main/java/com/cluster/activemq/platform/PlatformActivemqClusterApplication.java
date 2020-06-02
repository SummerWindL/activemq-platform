package com.cluster.activemq.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: activemq-platform
 * @description:
 * @author: fuyl
 * @create: 2020-06-01 22:21
 **/
@EnableAutoConfiguration
@ComponentScan(value = "com.cluster")
public class PlatformActivemqClusterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformActivemqClusterApplication.class);
    }
}
