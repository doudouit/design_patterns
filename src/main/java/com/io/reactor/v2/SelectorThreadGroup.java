package com.io.reactor.v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @decription: 线程组
 * @author: 180449
 * @date 2021/11/29 14:10
 */
public class SelectorThreadGroup {

    SelectorThread[] sts;
    ServerSocketChannel server = null;
    AtomicInteger xid = new AtomicInteger(0);

    SelectorThreadGroup(int num) {
        sts = new SelectorThread[num];
        for (int i = 0; i < num; i++) {
            sts[i] = new SelectorThread(this);

            new Thread(sts[i]).start();
        }
    }

    public void bind(int port) {

        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // 注册到那个selector上呢
            nextSelectorV2(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextSelector(Channel channel) {
        //在 main线程种，取到堆里的selectorThread对象
        SelectorThread st = next();

        // 1.通过队列传递数据 消息
        st.lbq.add(channel);
        // 2.通过打断阻塞，让对应的线程去自己在打断后完成注册selector
        st.selector.wakeup();
    }

    public void nextSelectorV2(Channel channel) {
        if (channel instanceof ServerSocketChannel) {
            sts[0].lbq.add(channel);
            sts[0].selector.wakeup();
        } else {
            //在 main线程种，取到堆里的selectorThread对象
            SelectorThread st = nextV2();

            // 1.通过队列传递数据 消息
            st.lbq.add(channel);
            // 2.通过打断阻塞，让对应的线程去自己在打断后完成注册selector
            st.selector.wakeup();
        }


    }

    /**
        无论 serversocket  socket  都复用这个方法
     */
    public SelectorThread next() {
        // 轮询就会很尴尬，倾斜
        int index = xid.incrementAndGet() % sts.length;
        return sts[index];
    }

    public SelectorThread nextV2() {
        // 轮询就会很尴尬，倾斜
        int index = xid.incrementAndGet() % (sts.length-1);
        return sts[index+1];
    }
}
