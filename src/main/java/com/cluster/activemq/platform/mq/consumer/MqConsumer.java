package com.cluster.activemq.platform.mq.consumer;


import com.cluster.activemq.platform.bean.MqCmd;
import com.cluster.activemq.platform.common.MqConst;
import com.cluster.activemq.platform.exception.PlatformCreateQueueException;
import com.cluster.activemq.platform.service.IMqhandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 20:47
 **/
@Slf4j
public class MqConsumer implements Runnable{
    private String mqType = "";
    private String mqName = "";
    private IMqConsumer consumer = null;
    private QueueConsumer queueConsumer = null;
    private TopicConsumer topicConsumer = null;
    private Map<String, Object> handleMap = new HashMap<String, Object>();


    public MqConsumer(String mqType, String mqName) {
        this.mqType = mqType;
        this.mqName = mqName;
        startThread();
    }

    public synchronized void addHandler(String cmdNo, IMqhandler iMqhandler) {
        if (StringUtils.isEmpty(cmdNo) && handleMap.containsKey(cmdNo)) {
            throw new PlatformCreateQueueException();
        }
        handleMap.put(cmdNo, iMqhandler);
    }
    private synchronized void handleMsg(MqCmd mqCmd) {
        if (handleMap.containsKey(mqCmd.getCmdNo())) {
            IMqhandler iMqhandler = (IMqhandler) handleMap.get(mqCmd.getCmdNo());
            iMqhandler.handle(mqCmd.getCmdNo(), mqCmd.getCmdMsg());
        }
    }

    private void startThread() {
        new Thread(this).start();
    }
    private IMqConsumer createConsumer(){
        if (MqConst.QUEUE.equals(mqType)) {
            consumer = new QueueConsumer(mqName);
        } else if (MqConst.TOPIC.equals(mqType)) {
            consumer = new TopicConsumer(mqName);
        }
        int nRet = consumer.initConsumer();
        if (MqConst.SUCCESS != nRet ){
            return null;
        }
        return consumer;

    }

    @Override
    public void run() {
        IMqConsumer consumer = null;
        boolean connected = false;

        while (true) {

            try {
                Thread.sleep(20);
            }catch (Exception e) {
                e.printStackTrace();
            }
            if(!connected){
                consumer = this.createConsumer();
                if (consumer == null){
                    connected = false;
                    continue;
                }
                connected =true;
            }
            MqCmd mqCmd = consumer.reviceMsg();
            if (mqCmd == null){
                connected = false;
                continue;
            }

            //此处线程内处理消息发生异常 业务级别错误 不应该退出线程 只记录错误日志 TODO 存数据库
            try{
                handleMsg(mqCmd);
            }catch (Exception e){
                log.error("cmdNo:{} 出错：{}",mqCmd.getCmdNo(),e.getMessage());
            }

        }
    }
}
