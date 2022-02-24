package com.mq.rocketmq.batchexample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @decription: 批量发送消息
 * @author: 180449
 * @date 2022/2/24 14:51
 */
public class BatchProducer {

    /**
     * 批量发送消息能显著提高传递小消息的性能。
     * 限制是这些批量消息应该有相同的topic，相同的waitStoreMsgOK，而且不能是延时消息。此外，这一批消息的总大小不应超过4MB。
     * @param args
     */
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        String topic = "BatchTest";
        producer.start();
        List<Message> messages = new ArrayList<>();
        /*messages.add(new Message(topic, "TagA", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "TagA", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "TagA", "OrderID003", "Hello world 2".getBytes()));
        try {
            producer.send(messages);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }*/
        for (int i = 0; i < 1000000; i++) {
            messages.add(new Message(topic, "TagA", "OrderID" + i, ("Hello world" + i).getBytes(StandardCharsets.UTF_8)));
        }
        // 消息过大，把大的消息分裂成若干个小的消息
        ListSplitter splitter = new ListSplitter(messages);
        while(splitter.hasNext()){
            try{
                List<Message> listItem = splitter.next();
                producer.send(listItem);
            }catch(Exception e){
                e.printStackTrace();
                //处理error
            }
        }
        producer.shutdown();
    }
}
