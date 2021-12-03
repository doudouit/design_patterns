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


import com.io.rpcrefactor.proxy.MyProxy;
import com.io.rpcrefactor.rpc.Dispatcher;
import com.io.rpcrefactor.rpc.protocol.MyContent;
import com.io.rpcrefactor.service.*;
import com.io.rpcrefactor.util.SerDerUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

                        // 1 自定义rpc 在自己定义协议的时候你关注过哪些问题：粘包拆包的问题，header+body
                        // pipeline.addLast(new ServerDecode());
                        // pipeline.addLast(new ServerRequestHandler(dis));

                        // 2 小火车， 传输协议http
                        pipeline.addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(1024*512))
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        // http协议， msg就是一个完整的 http-request
                                        FullHttpRequest request = (FullHttpRequest) msg;
                                        System.out.println(request.toString());

                                        ByteBuf content = request.content();
                                        byte[] data = new byte[content.readableBytes()];
                                        content.readBytes(data);
                                        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
                                        MyContent myContent = (MyContent) oin.readObject();

                                        String serviceName = myContent.getName();
                                        String method = myContent.getMethodName();
                                        Object c = dis.get(serviceName);
                                        Class<?> clazz = c.getClass();
                                        Object res = null;
                                        try {
                                            Method m = clazz.getMethod(method, myContent.getParameterTypes());
                                            res = m.invoke(c, myContent.getArgs());
                                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                            e.printStackTrace();
                                        }

                                        MyContent resContent = new MyContent();
                                        resContent.setRes(res);
                                        byte[] resContentByte = SerDerUtil.ser(resContent);

                                        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                                                HttpResponseStatus.OK,
                                                Unpooled.copiedBuffer(resContentByte));

                                        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, resContentByte.length);

                                        ctx.writeAndFlush(response);
                                    }
                                });
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
















