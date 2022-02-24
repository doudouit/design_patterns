package com.mq.rocketmq.filterexample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @decription: 过滤消息
 * @author: 180449
 * @date 2022/2/24 16:36
 */
public class FilterProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC"};
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("TopicTest",
                    tags[i % tags.length],
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // 设置一些属性
            msg.putUserProperty("a", String.valueOf(i));
            SendResult sendResult = producer.send(msg);
        }

        producer.shutdown();
    }
}
