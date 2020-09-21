package com.cluster.activemq.platform.service.biz.test;


import com.cluster.activemq.platform.config.ActiveMqProperties;
import com.cluster.activemq.platform.mq.consumer.MqConsumer;
import com.cluster.activemq.platform.mq.producer.MqProducer;
import com.cluster.activemq.platform.service.IMqhandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: middle-server
 * @description:
 * @author: fuyl
 * @create: 2020-06-16 14:54
 **/
@Slf4j
@Component
public class ModeServiceImpl {
    //通过配置文件控制 生产类型
    @Qualifier(value = "activeMqProperties")
    @Resource
    private ActiveMqProperties config;

    @Autowired
    private ServiceFactory serviceFactory;
    private static final String PRODUCER = "producer";
    private static final String CONSUMER = "consumer";

    private static volatile int count=0;

    /**
     * 初始化模式 生产者（topic,queue）/消费者（topic,queue）
     */
    public void initModeService(){
        if(PRODUCER.equals(config.getServiceType())){
            ProducerService producerService = (ProducerService) serviceFactory.getService(config.getServiceType());
            MqProducer mqProducer = producerService.initService(config.getMqType(), config.getMqName());
            log.info("=======创建producer实例完成=======");
            mqProducer.startProducer(); //启动
            cyclePushMsg(mqProducer);
        }else if(CONSUMER.equals(config.getServiceType())){
            ConsumerService consumerService = (ConsumerService) serviceFactory.getService(config.getServiceType());
            MqConsumer mqConsumer = consumerService.initService(config.getMqType(), config.getMqName());
            log.info("=======创建consumer实例完成=======");
            mqConsumer.addHandler(config.getCmdNo(),new Handler());
        }

    }

    /**
     * 循环发送消息
     * @param mqProducer
     */
    private void cyclePushMsg(final MqProducer mqProducer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    count++;
                    try {
                        Thread.sleep(config.getSleepTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mqProducer.pushMsg(config.getCmdNo(),"update","第"+count+"条消息");
                }
            }
        }).start();
    }


}
@Slf4j
@Component
class Handler implements IMqhandler {

    @Override
    public void handle(String cmdNo, String cmdMsg) {
        log.info("收到cmdNo：{} 的消息：{}",cmdNo,cmdMsg);
    }
}



