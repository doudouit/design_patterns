package com.mq.rocketmq.simpleexample.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @decription: 异步生产者
 * @author: 180449
 * @date 2022/2/23 15:04
 */
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messagecount = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(messagecount);
        for (int i = 0; i < messagecount; i++) {
            final int index = i;
            try {
                Message msg = new Message("Jodie_topic_1023",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            } catch (UnsupportedEncodingException | RemotingException | InterruptedException e) {
                e.printStackTrace();
            }

        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
