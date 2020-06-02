package com.cluster.activemq.platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: activemq-platform
 * @description:
 * @author: fuyl
 * @create: 2020-06-01 22:22
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformActivemqClusterApplication.class)
public class PlatformActivemqClusterApplicationTest {
    @Test
    public void contextLoads() {
        System.out.println("hello activemq");
    }
}
