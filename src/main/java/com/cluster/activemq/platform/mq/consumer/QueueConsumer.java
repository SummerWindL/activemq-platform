package com.cluster.activemq.platform.mq.consumer;


import com.cluster.activemq.platform.bean.MqCmd;
import com.cluster.activemq.platform.common.MqConst;
import com.cluster.activemq.platform.config.ActiveMqProperties;
import com.cluster.activemq.platform.config.ConfigService;
import net.sf.json.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 16:13
 **/
public class QueueConsumer implements IMqConsumer{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static String BROKEN_URL = "";

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageConsumer consumer = null;

    private String queueName = "";
    ExecutorService threadPool = Executors.newFixedThreadPool(1);

    private ActiveMqProperties activeMqProperties = ConfigService.getBean("activeMqProperties");


    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<MessageConsumer>();

    public QueueConsumer(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public void destory() {
        try{
            if (consumer != null) {
                consumer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }catch (JMSException e){
            e.printStackTrace();
        }
        consumer = null;
        session = null;
        connection = null;
    }

    @Override
    public int initConsumer() {
        try {
            logger.info("开始连接activemq服务器 地址：{}", activeMqProperties.getBrokerUrl());
            USERNAME = activeMqProperties.getUser();
            PASSWORD = activeMqProperties.getPassword();
            BROKEN_URL = activeMqProperties.getBrokerUrl();

            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(this.queueName);
            consumer = session.createConsumer(queue);
        } catch (Exception e) {
            this.destory();
            e.printStackTrace();
            return MqConst.FAILED;
        }
        return MqConst.SUCCESS;
    }

    @Override
    public MqCmd reviceMsg() {
        MqCmd mqCmd= null;
        String cmdNo = "";
        String cmdMsg = "";
        try {
            TextMessage msg = (TextMessage) consumer.receive();
            msg.acknowledge();
            JSONObject jsonObject = JSONObject.fromObject(msg.getText());
            cmdNo = jsonObject.get("cmdNo").toString();
            cmdMsg = jsonObject.get("cmdMsg").toString();
            mqCmd= new MqCmd();
            mqCmd.setCmdNo(cmdNo);
            mqCmd.setCmdMsg(cmdMsg);
        } catch (Exception e) {
            logger.error("cmdNo：{} 接收出错：{}",cmdNo,e.getMessage());
            e.printStackTrace();
            return null;
        }
        return mqCmd;
    }



}
