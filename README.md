# activemq-platform
activemq简单封装 动态传递部分参数

# Use it

Should execute mvn install to localrepo

## Producer
```
MqProducer mqProducer = new MqProducer(MqConst.TOPIC, "ikinloop_topic_1");

        //for(int i=1;i<=100000000;i++)
        while (true) {
            Thread.sleep(1000);
            mqProducer.pushMsg("cmd_10001", "{'key':'预警消息'}");
        }

```

## Consumer
```
MqConsumer mqConsumer = new MqConsumer(MqConst.TOPIC, "ikinloop_topic_1");
//业务处理
mqConsumer.addHandler("cmd_10001", new Cmd10001Handler());
//接收
CountDownLatch countDownLatch = new CountDownLatch(10);
countDownLatch.await();
```

业务处理 eg:
```
public class Cmd10001Handler implements IMqhandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(String cmdNo, String cmdMsg) throws IkinloopCreateQueueException {
        logger.info("CmdNo:{} ===== CmdMsg:{}", cmdNo, cmdMsg);
    }
}
```