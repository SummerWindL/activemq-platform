package com.cluster.activemq.platform.mq.consumer;


import com.cluster.activemq.platform.bean.MqCmd;

/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-06-01 20:39
 **/
public interface IMqConsumer {

    public int initConsumer();

    public MqCmd reviceMsg();


    void destory();
}
