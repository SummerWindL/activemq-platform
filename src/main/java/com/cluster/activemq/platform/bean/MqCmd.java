package com.cluster.activemq.platform.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 19:18
 **/
@Data
public class MqCmd implements Serializable {

    private String cmdNo;
    private String cmdMsg;
}
