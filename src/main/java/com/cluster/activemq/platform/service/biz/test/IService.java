package com.cluster.activemq.platform.service.biz.test;

/**
 * @program: middle-server
 * @description:
 * @author: fuyl
 * @create: 2020-06-16 14:29
 **/

public interface IService {

    /**
     * 根据type类型返回对应的Service
     * @param type
     * @param mqName
     * @param <T>
     * @return
     */
    <T> T initService(String type,String mqName);
}
