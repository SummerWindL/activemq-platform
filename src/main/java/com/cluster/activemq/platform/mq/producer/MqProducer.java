package com.cluster.activemq.platform.mq.producer;

import com.cluster.activemq.platform.bean.MqCmd;
import com.cluster.activemq.platform.common.MqConst;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: platform-ikinloop-activemq
 * @description:
 * @author: fuyl
 * @create: 2020-05-28 20:34
 **/

public class MqProducer implements Runnable{
    private String mqType = "";
    private String mqName = "";
    private IMqProducer producer = null;
    private boolean persistentflag = true;
    private ExecutorService threadPool = Executors.newFixedThreadPool(1);
    private Queue queue = new LinkedBlockingQueue();


    public MqProducer(String mqType, String mqName) {
        this.mqType = mqType;
        this.mqName = mqName;
        this.startThread();
    }

    public MqProducer(String mqType, String mqName, boolean persistentflag) {
        this.mqType = mqType;
        this.mqName = mqName;
        this.persistentflag = persistentflag;
        this.startThread();
    }

    public synchronized void pushMsg(String cmdNo, String message) {
        MqCmd mqCmd = new MqCmd();
        mqCmd.setCmdNo(cmdNo);
        mqCmd.setCmdMsg(message);
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
        new Thread(this).start();

    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        IMqProducer producer = null;
        boolean connected = false;

        while (true) {

            try {
                Thread.sleep(20);
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
            int nRet = producer.pushMsg(mqCmd.getCmdNo(), mqCmd.getCmdMsg());
            if (MqConst.SUCCESS != nRet){
                producer.destory();
                connected = false;
                continue;
            }
            removeMsg(mqCmd);
        }
    }
}
