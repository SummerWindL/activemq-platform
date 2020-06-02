package com.cluster.activemq.platform.activemq;

import com.cluster.activemq.platform.PlatformActivemqClusterApplicationTest;
import com.cluster.activemq.platform.config.ActiveMqProperties;
import com.cluster.activemq.platform.mq.consumer.TopicConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 17:29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformActivemqClusterApplicationTest.class)
public class TopicConsumerTest {

    @Autowired
    private ActiveMqProperties activeMqProperties;

    @Test
    public void test() throws Exception {
        TopicConsumer mqConsumer = new TopicConsumer("topic_no_persistent");
        Cmd10001Handler handler = new Cmd10001Handler();
//        mqConsumer.addHandler("cmd_11111", handler);
//        mqConsumer.start();
        System.in.read();
    }
}
