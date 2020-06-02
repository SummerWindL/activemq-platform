package com.cluster.activemq.platform.activemq;

import com.cluster.activemq.platform.PlatformActivemqClusterApplicationTest;
import com.cluster.activemq.platform.common.MqConst;
import com.cluster.activemq.platform.mq.consumer.MqConsumer;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-05-29 09:58
 **/
public class MqConsumerTest extends PlatformActivemqClusterApplicationTest {
    @Test
    public void test() throws IOException, InterruptedException {
        MqConsumer mqConsumer = new MqConsumer(MqConst.TOPIC, "ikinloop_topic_1");

        //业务处理
        mqConsumer.addHandler("cmd_10001", new Cmd10001Handler());
        //接收
        CountDownLatch countDownLatch = new CountDownLatch(10);
        countDownLatch.await();

    }
}
