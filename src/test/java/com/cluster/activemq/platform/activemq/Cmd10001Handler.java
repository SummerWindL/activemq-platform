package com.cluster.activemq.platform.activemq;

import com.cluster.activemq.platform.service.IMqhandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-27 19:37
 **/
public class Cmd10001Handler implements IMqhandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(String cmdNo, String cmdMsg){
        logger.info("CmdNo:{} ===== CmdMsg:{}", cmdNo, cmdMsg);
    }
}
