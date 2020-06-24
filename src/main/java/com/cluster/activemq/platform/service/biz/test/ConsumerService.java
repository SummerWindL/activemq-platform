package com.cluster.activemq.platform.service.biz.test;

import com.cluster.activemq.platform.mq.consumer.MqConsumer;
import org.springframework.stereotype.Component;

/**
 * @program: middle-server
 * @description:
 * @author: fuyl
 * @create: 2020-06-16 14:37
 **/
@Component
public class ConsumerService implements IService {
    /**
     * 根据type类型返回对应的Service
     *
     * @param type
     * @return
     */
    @Override
    public <T> T initService(String type,String mqName) {
        MqConsumer mqConsumer = new MqConsumer(type,mqName);
        return (T)mqConsumer;
    }
}
