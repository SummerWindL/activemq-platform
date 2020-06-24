package com.cluster.activemq.platform.mq.producer;

/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-06-01 19:52
 **/
public interface IMqProducer {
    public int initProducer();
    public int pushMsg(String cmdNo,String cmdType, String message);
    public void destory();
}
