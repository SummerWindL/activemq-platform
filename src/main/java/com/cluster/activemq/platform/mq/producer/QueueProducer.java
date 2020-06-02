package com.cluster.activemq.platform.mq.producer;

import com.cluster.activemq.platform.bean.MqCmd;
import com.cluster.activemq.platform.common.MqConst;
import com.cluster.activemq.platform.config.ActiveMqProperties;
import com.cluster.activemq.platform.config.ConfigService;
import net.sf.json.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.jms.*;


/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 16:02
 **/

public class QueueProducer implements IMqProducer{

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ActiveMQConnectionFactory connectionFactory = null;
    private ActiveMqProperties activeMqProperties = ConfigService.getBean("activeMqProperties");
    private Connection connection = null;
    private MessageProducer producer = null;
    private Session session = null;
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static String BROKEN_URL = "";
    private String queueName="";
    private boolean persistentflag = true;
    public QueueProducer(String queueName) {
        this.queueName = queueName;
    }

    public QueueProducer(String queueName, boolean persistentflag) {

        this.queueName = queueName;
        this.persistentflag = persistentflag;
    }

    @Override
    public int initProducer() {

        try {
            logger.info("开始连接activemq服务器 地址：{}", activeMqProperties.getBrokerUrl());
            USERNAME = activeMqProperties.getUser();
            PASSWORD = activeMqProperties.getPassword();
            BROKEN_URL = activeMqProperties.getBrokerUrl();
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
            connection = connectionFactory.createConnection();
            //3.启动连接
            connection.start();

            //4.创建会话
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个目标
            Destination destination = session.createQueue(this.queueName);
            producer = session.createProducer(destination);
            if (this.persistentflag) {
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            } else {
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
        } catch (Exception e) {
            this.destory();
            e.printStackTrace();
            return MqConst.FAILED;
        }
        return MqConst.SUCCESS;

    }
    @Override
    public void destory()  {
        try{
            if (producer != null) {
                producer.close();
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
        producer = null;
        session = null;
        connection = null;
    }
    @Override
    public int pushMsg(String cmdNo, String message){
        try {
            MqCmd mqCmd = new MqCmd();
            mqCmd.setCmdNo(cmdNo);
            mqCmd.setCmdMsg(message);
            JSONObject jsonObject = JSONObject.fromObject(mqCmd);
            TextMessage msg = session.createTextMessage(jsonObject.toString());
            producer.send(msg);
        } catch (Exception e) {
            this.destory();
            e.printStackTrace();
            return MqConst.FAILED;
        }
        return MqConst.SUCCESS;

    }
}
