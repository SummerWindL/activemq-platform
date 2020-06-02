package com.cluster.activemq.platform.service;

import com.cluster.activemq.platform.exception.IkinloopCreateQueueException;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 18:01
 **/

public interface IMqhandler {
    public void handle(String cmdNo, String cmdMsg) throws IkinloopCreateQueueException;
}
