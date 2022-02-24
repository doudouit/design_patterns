在大多数情况下，TAG是一个简单而有用的设计，其可以来选择您想要的消息。例如：
```java
DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("CID_EXAMPLE");
consumer.subscribe("TOPIC", "TAGA || TAGB || TAGC");
```

消费者将接收包含TAGA或TAGB或TAGC的消息。但是限制是一个消息只能有一个标签，这对于复杂的场景可能不起作用。在这种情况下，可以使用SQL表达式筛选消息。SQL特性可以通过发送消息时的属性来进行计算。在RocketMQ定义的语法下，可以实现一些简单的逻辑。下面是一个例子：
```sql92
------------
| message  |
|----------|  a > 5 AND b = 'abc'
| a = 10   |  --------------------> Gotten
| b = 'abc'|
| c = true |
------------
------------
| message  |
|----------|   a > 5 AND b = 'abc'
| a = 1    |  --------------------> Missed
| b = 'abc'|
| c = true |
------------
```

开启配置broker.conf
```
enablePropertyFilter=true

// 启动方式
start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true -c ../conf/broker.conf 
```

只有使用push模式的消费者才能用使用SQL92标准的sql语句，接口如下：
```java
public void subscribe(finalString topic, final MessageSelector messageSelector)
```

