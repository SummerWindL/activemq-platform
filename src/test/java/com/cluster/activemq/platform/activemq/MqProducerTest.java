package com.cluster.activemq.platform.activemq;

import com.cluster.activemq.platform.PlatformActivemqClusterApplication;
import com.cluster.activemq.platform.common.MqConst;
import com.cluster.activemq.platform.mq.producer.MqProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: platform-base
 * @description:
 * @author: fuyl
 * @create: 2020-05-29 09:55
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformActivemqClusterApplication.class)
public class MqProducerTest {

    @Test
    public void test() throws InterruptedException {
        MqProducer mqProducer = new MqProducer(MqConst.TOPIC, "ikinloop_topic_1");

        //for(int i=1;i<=100000000;i++)
        while (true) {
            Thread.sleep(1000);
            mqProducer.pushMsg("cmd_10001", "update","{'key':'预警消息'}");
        }
    }

}
