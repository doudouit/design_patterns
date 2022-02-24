package com.mq.rocketmq.simpleexample.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @decription: 同步生产者
 * @author: 180449
 * @date 2022/2/23 14:39
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("TopicTest", "tagsA",
                    ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result = producer.send(message);
            System.out.printf("%s%n", result);
        }
        producer.shutdown();
    }
}
