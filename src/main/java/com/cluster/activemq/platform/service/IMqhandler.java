package com.cluster.activemq.platform.service;


/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 18:01
 **/

public interface IMqhandler {
    public void handle(String cmdNo, String cmdMsg);
}
