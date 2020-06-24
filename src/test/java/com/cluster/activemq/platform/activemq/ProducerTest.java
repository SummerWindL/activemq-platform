package com.cluster.activemq.platform.activemq;

import com.cluster.activemq.platform.PlatformActivemqClusterApplicationTest;
import com.cluster.activemq.platform.config.ActiveMqProperties;
import com.cluster.activemq.platform.mq.producer.QueueProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 16:28
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformActivemqClusterApplicationTest.class)
public class ProducerTest {

    @Autowired
    private ActiveMqProperties activeMqProperties;

    @Test
    public void test() throws Exception {
        QueueProducer mqProducer = new QueueProducer("cmd_10001");
        for (int i = 1; i < 100; i++) {
            mqProducer.pushMsg("cmd_11111", "update","11111");
        }
        System.out.println("消息发送完成====");
    }

}
