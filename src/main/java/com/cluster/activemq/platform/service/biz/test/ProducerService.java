package com.cluster.activemq.platform.service.biz.test;

import com.cluster.activemq.platform.mq.producer.MqProducer;
import org.springframework.stereotype.Component;

/**
 * @program: middle-server
 * @description:
 * @author: fuyl
 * @create: 2020-06-16 14:34
 **/
@Component
public class ProducerService implements IService{
    /**
     * 根据type类型返回对应的Service
     * @param type
     * @return
     */
    @Override
    public <T> T initService(String type,String mqName) {
        MqProducer mqProducer = new MqProducer(type,mqName);
        return (T)mqProducer;
    }
}
