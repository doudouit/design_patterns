package com.io.rpcrefactor;

/**
 * @decription: rpc模拟
 * @author: 180449
 * @date 2021/11/30 16:56
 */

/*
    question:
    1，先假设一个需求，写一个RPC
    2，来回通信，连接数量，拆包？
    3，动态代理呀，序列化，协议封装
    4，连接池
    5，就像调用本地方法一样去调用远程的方法，面向java中就是所谓的 面向interface开发
 */


import com.io.rpcrefactor.service.*;
import com.io.rpcrefactor.proxy.MyProxy;
import com.io.rpcrefactor.rpc.Dispatcher;
import com.io.rpcrefactor.rpc.transport.ServerDecode;
import com.io.rpcrefactor.rpc.transport.ServerRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 上节课，基本写了一个能发送
 * 小问题，当并发通过一个连接发送后，服务端解析bytebuf 转 对象的过程出错
 */
public class MyRpcTest {

    @Test
    public void startServer() {
        Car car = new MyCar();
        Fly fly = new MyFly();
        Dispatcher dis = Dispatcher.getDis();
        dis.register(Car.class.getName(), car);
        dis.register(Fly.class.getName(), fly);

        NioEventLoopGroup boss = new NioEventLoopGroup(10);
        NioEventLoopGroup worker = boss;

        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //System.out.println("server accept client port: " + ch.remoteAddress().getPort());
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ServerDecode());
                        pipeline.addLast(new ServerRequestHandler(dis));
                    }
                })
                .bind(new InetSocketAddress("127.0.0.1", 9090));

        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟Consumer
     */
    @Test
    public void get() {

        //new Thread(this::startServer).start();

        System.out.println("server start......");

        AtomicInteger num = new AtomicInteger(0);
        int size = 20;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(() -> {
                Car car = MyProxy.proxyGet(Car.class);
                String arg = "hello" + num.incrementAndGet();
                String res = car.ooxx(arg);
                System.out.println("client over msg: " + res + ", src arg: " + arg);
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRPC() {
        Car car = MyProxy.proxyGet(Car.class);
        Persion zhangsan = car.oxox("zhangsan", 16);
        System.out.println(zhangsan);
    }

    @Test
    public void testRpcLocal() {
        new Thread(() -> {
            startServer();
        }).start();

        System.out.println("server started......");

        Car car = MyProxy.proxyGet(Car.class);
        Persion zhangsan = car.oxox("zhangsan", 16);
        System.out.println(zhangsan);
    }
}
















