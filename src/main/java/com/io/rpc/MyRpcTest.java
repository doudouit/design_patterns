package com.io.rpc;

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

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 上节课，基本写了一个能发送
 * 小问题，当并发通过一个连接发送后，服务端解析bytebuf 转 对象的过程出错
 */
public class MyRpcTest {


    @Test
    public void startServer() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = boss;

        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept client port: " + ch.remoteAddress().getPort());
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ServerDecode());
                        pipeline.addLast(new ServerRequestHandler());
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

        new Thread(this::startServer).start();

        System.out.println("server start......");

        AtomicInteger num = new AtomicInteger(0);
        int size = 20;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(() -> {
                Car car = proxyGet(Car.class);
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

    public static <T> T proxyGet(Class<T> interfaceInfo) {
        // 实现动态代理

        ClassLoader loader = interfaceInfo.getClassLoader();
        Class<?>[] methedInfo = {interfaceInfo};

        return (T) Proxy.newProxyInstance(loader, methedInfo, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 设计consumer对provider的调用过程
                //1，调用 服务，方法，参数  ==》 封装成message  [content]
                String name = interfaceInfo.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                MyContent conetnt = new MyContent(name, methodName, parameterTypes, args);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream oout = new ObjectOutputStream(out);
                oout.writeObject(conetnt);
                byte[] msgBody = out.toByteArray();

                //2，requestID+message  ，本地要缓存
                //协议：【header<>】【msgBody】
                MyHeader header = createHeader(msgBody);

                out.reset();
                oout = new ObjectOutputStream(out);
                oout.writeObject(header);
                //  数据decode问题
                // todo server dispatcher executor
                byte[] msgHeader = out.toByteArray();

                // System.out.println("msgheader: " + msgHeader.length);

                // 3. 连接池获得连接
                ClientFactory factory = ClientFactory.getFactory();
                NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("127.0.0.1", 9090));

                // 4. 发送--> 走IO out --> 走netty
                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);

                //CountDownLatch countDownLatch = new CountDownLatch(1);
                long requestID = header.getRequestID();

                CompletableFuture<String> res = new CompletableFuture<>();
                // runnable后面才会启动
                ResponseHandler.addCallBack(requestID, res);

                byteBuf.writeBytes(msgHeader);
                byteBuf.writeBytes(msgBody);
                ChannelFuture channelFuture = clientChannel.writeAndFlush(byteBuf);
                // io是双向的，看似有个sync，她仅代表out
                channelFuture.sync();

                //countDownLatch.await();

                //5，？，如果从IO ，未来回来了，怎么将代码执行到这里
                //（睡眠/回调，如何让线程停下来？你还能让他继续。。。）

                // 阻塞
                return res.get();
            }
        });
    }

    private static MyHeader createHeader(byte[] msg) {
        int flag = 0x14141414;
        int size = msg.length;
        long requestID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        return new MyHeader(flag, requestID, size);
    }

}

class ClientFactory {
    int poolSize = 1;
    NioEventLoopGroup clientWorker;
    Random random = new Random();

    // 单例的
    private static final ClientFactory factory;

    private ClientFactory() {
    }

    static {
        factory = new ClientFactory();
    }

    public static ClientFactory getFactory() {
        return factory;
    }

    /**
     * 一个consumer可以连接很多的provider，每一个consumer都有自己的pool K,V
     */
    ConcurrentHashMap<InetSocketAddress, ClientPool> outboxs = new ConcurrentHashMap<>();

    public synchronized NioSocketChannel getClient(InetSocketAddress address) {
        ClientPool clientPool = outboxs.get(address);
        if (clientPool == null) {
            outboxs.put(address, new ClientPool(poolSize));
            clientPool = outboxs.get(address);
        }

        // 随机选一个
        int i = random.nextInt(poolSize);

        if (clientPool.clients[i] != null && clientPool.clients[i].isActive()) {
            return clientPool.clients[i];
        }

        synchronized (clientPool.lock[i]) {
            return clientPool.clients[i] = createClient(address);
        }
    }

    private NioSocketChannel createClient(InetSocketAddress address) {
        // 基于netty客户端的创建方式
        clientWorker = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(clientWorker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ServerDecode());
                        // 解决给谁的？？  requestID
                        pipeline.addLast(new ClientResponses());
                    }
                }).connect(address);

        try {
            NioSocketChannel client = (NioSocketChannel) connect.sync().channel();
            return client;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}


/**
 * 客户端连接池
 */
class ClientPool {
    NioSocketChannel[] clients;
    // 伴生锁
    Object[] lock;

    ClientPool(int size) {
        // init 连接都是空的
        clients = new NioSocketChannel[size];
        // 锁是可以初始化的
        lock = new Object[size];
        for (int i = 0; i < size; i++) {
            lock[i] = new Object();
        }
    }
}

class MyHeader implements Serializable {

    private static final long serialVersionUID = -8874173164157264719L;

    //通信上的协议
    /*
    1，ooxx值
    2，UUID:requestID
    3，DATA_LEN

     */
    int flag;  //32bit可以设置很多信息。。。
    long requestID;
    long dataLen;

    public MyHeader() {
    }

    public MyHeader(int flag, long requestID, long dataLen) {
        this.flag = flag;
        this.requestID = requestID;
        this.dataLen = dataLen;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public long getDataLen() {
        return dataLen;
    }

    public void setDataLen(long dataLen) {
        this.dataLen = dataLen;
    }

}

class MyContent implements Serializable {
    private static final long serialVersionUID = 3622067262756634471L;

    private String name;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] args;
    private String res;

    public MyContent() {
    }

    public MyContent(String name, String methodName, Class<?>[] parameterTypes, Object[] args) {
        this.name = name;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.args = args;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}

/**
 * 拆包
 */
class ServerDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        while(buf.readableBytes() >= 89) {
            byte[] bytes = new byte[89];
            buf.getBytes(buf.readerIndex(),bytes);  //从哪里读取，读多少，但是readindex不变
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(in);
            MyHeader header = (MyHeader) oin.readObject();


            //DECODE在2个方向都使用
            //通信的协议
            if(buf.readableBytes() >= header.getDataLen()){
                //处理指针
                buf.readBytes(89);  //移动指针到body开始的位置
                byte[] data = new byte[(int)header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);
                MyContent content = (MyContent) doin.readObject();

                out.add(new Packmsg(header,content));
            }else{
                break;
            }


        }

    }
}

class ResponseHandler {

    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestID, CompletableFuture cb) {
        mapping.put(requestID, cb);
    }

    public static void runCallBack(Packmsg packmsg) {
        long requestID = packmsg.header.getRequestID();
        CompletableFuture cb = mapping.get(requestID);
        cb.complete(packmsg.content.getRes());
        removeCB(requestID);
    }

    public static void removeCB(long requestID) {
        mapping.remove(requestID);
    }

}

class ClientResponses extends ChannelInboundHandlerAdapter {

    // consumer
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packmsg responsePkg = (Packmsg) msg;

        ResponseHandler.runCallBack(responsePkg);

    }
}

class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    // provider
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packmsg requestPkg = (Packmsg) msg;

        Object arg = requestPkg.getContent().getArgs()[0];
        // System.out.println("server handler :" + requestPkg.content.getMethodName());
        // System.out.println("server handler :" + requestPkg.content.getArgs()[0]);


        String ioThreadName = Thread.currentThread().getName();
        // 给客户端返回
        // 1. 直接当前方法 处理IO和业务返回
        // 2. 创建自己的线程池
        // 3. 使用netty自己的eventloop来处理业务及返回
        ctx.executor().execute(() -> {
            String execThreadName = Thread.currentThread().getName();
            MyContent content = new MyContent();
            String res = "io thread: " + ioThreadName + "exec Thread: " + execThreadName + " from args: " + arg;
            content.setRes(res);
            byte[] contentByte = SerDerUtil.ser(content);

            MyHeader resHeader = new MyHeader();
            resHeader.setRequestID(requestPkg.header.getRequestID());
            resHeader.setFlag(0x14141424);
            resHeader.setDataLen(contentByte.length);
            byte[] headerByte = SerDerUtil.ser(resHeader);
            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + contentByte.length);

            byteBuf.writeBytes(headerByte);
            byteBuf.writeBytes(contentByte);
            ctx.writeAndFlush(byteBuf);
        });



    }
}

class MyCar implements Car {

    @Override
    public String ooxx(String msg) {
        System.out.println("server,get client arg:" + msg);
        return "server res " + msg;
    }
}

class MyFly implements Fly {

    @Override
    public void xxoo(String msg) {
        System.out.println("server,get client arg:" + msg);
    }
}

interface Car {
    public String ooxx(String msg);
}

interface Fly {
    void xxoo(String msg);
}