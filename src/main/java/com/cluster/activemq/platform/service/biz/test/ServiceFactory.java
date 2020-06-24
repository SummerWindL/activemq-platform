package com.cluster.activemq.platform.service.biz.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @program: middle-server
 * @description:
 * @author: fuyl
 * @create: 2020-06-16 14:32
 **/
@Component
public class ServiceFactory {
    @Autowired
    @Qualifier("producerService")
    private IService producerService;
    @Autowired
    @Qualifier("consumerService")
    private IService consumerService;

    private static final String PRODUCER = "producer";
    private static final String CONSUMER = "consumer";

    /**
     * 获取到不同的Service
     * @param serviceType
     */
    public IService getService(String serviceType){
        if(PRODUCER.equals(serviceType)){
            return producerService;
        }else if(CONSUMER.equals(serviceType)){
            return consumerService;
        }
        return null;
    }
}
