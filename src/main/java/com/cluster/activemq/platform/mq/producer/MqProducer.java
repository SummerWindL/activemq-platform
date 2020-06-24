package com.cluster.activemq.platform.mq.producer;

import com.cluster.activemq.platform.bean.MqCmd;
import com.cluster.activemq.platform.common.MqConst;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 20:34
 **/

public class MqProducer implements Callable {
    private String mqType = "";
    private String mqName = "";
    private IMqProducer producer = null;
    private boolean persistentflag = true;
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private Queue queue = new ConcurrentLinkedQueue();


    public MqProducer(String mqType, String mqName) {
        this.mqType = mqType;
        this.mqName = mqName;

    }

    public MqProducer(String mqType, String mqName, boolean persistentflag) {
        this.mqType = mqType;
        this.mqName = mqName;
        this.persistentflag = persistentflag;
    }

    public void startProducer(){
        this.startThread();
    }

    public synchronized void pushMsg(String cmdNo, String cmdType, String cmdMsg) {
        MqCmd mqCmd = new MqCmd();
        mqCmd.setCmdNo(cmdNo);
        mqCmd.setCmdType(cmdType);
        mqCmd.setCmdMsg(cmdMsg);
        if (this.queue.size() > 5000){
            this.queue.remove();
        }
        this.queue.add(mqCmd);
    }

    private synchronized MqCmd peekMsg() {
        MqCmd mqCmd = (MqCmd)this.queue.peek();
        return mqCmd;
    }
    private synchronized void removeMsg(MqCmd mqCmd) {
        queue.remove(mqCmd);
    }

    private IMqProducer createProducer(){
        if (MqConst.TOPIC.equals(mqType)) {
            producer = new TopicProducer(this.mqName, this.persistentflag);
        } else if (MqConst.QUEUE.equals(mqType)) {
            producer = new QueueProducer(this.mqName, this.persistentflag);
        }
        int nRet = producer.initProducer();
        if (MqConst.SUCCESS != nRet ){
            return null;
        }

        return producer;

    }
    private void startThread(){
//        new Thread(this).start();

        FutureTask<MqProducer> task = new FutureTask<MqProducer>(this);
        executor.submit(task);

    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {
        IMqProducer producer = null;
        boolean connected = false;

        while (true) {

            try {
                Thread.sleep(100);
            }catch (Exception e) {
                e.printStackTrace();
            }

            if(!connected){
                producer = this.createProducer();
                if (producer == null){
                    connected = false;
                    continue;
                }
                connected =true;
            }
            MqCmd mqCmd = this.peekMsg();
            if (mqCmd == null){
                continue;
            }
            int nRet = producer.pushMsg(mqCmd.getCmdNo(),mqCmd.getCmdType(), mqCmd.getCmdMsg());
            if (MqConst.SUCCESS != nRet){
                producer.destory();
                connected = false;
                continue;
            }
            removeMsg(mqCmd);
        }
    }
}
