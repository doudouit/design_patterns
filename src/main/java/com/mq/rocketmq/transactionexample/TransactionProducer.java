package com.mq.rocketmq.transactionexample;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @decription 事务消息 生产者
 * @author 180449
 * @date 2022/2/25 9:10
 */
public class TransactionProducer {


    /**
     It can be thought of as a two-phase commit message implementation to ensure eventual consistency in distributed system.
     Transactional message ensures that the execution of local transaction and the sending of message can be performed atomically.
     它可以被认为是一个两阶段的提交消息实现，以确保分布式系统中的最终一致性。事务性消息保证了本地事务的执行和消息的发送能够以原子的方式进行
     */
    public static void main(String[] args) throws MQClientException, InterruptedException {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });

        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.start();

        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message message = new Message("TopicTest1234", tags[i % tags.length], "KEY" + i,
                        ("Hello RocketMq" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                TransactionSendResult sendResult = producer.sendMessageInTransaction(message, null);
                System.out.printf("%s%n", sendResult);
                Thread.sleep(10);
            } catch (UnsupportedEncodingException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}
