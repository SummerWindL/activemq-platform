package com.cluster.activemq.platform.activemq;

import com.cluster.activemq.platform.PlatformActivemqClusterApplicationTest;
import com.cluster.activemq.platform.mq.producer.TopicProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 17:28
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformActivemqClusterApplicationTest.class)
public class TopicProducerTest {

    @Test
    public void test() throws Exception {
        TopicProducer mqProducer = new TopicProducer("topic_2222", false);
        for (int i = 1; i <= 10; i++) {
            mqProducer.pushMsg("cmd_11111", "11111");
        }
        System.out.println("消息发送完成====");
        mqProducer.destory();
    }
}
