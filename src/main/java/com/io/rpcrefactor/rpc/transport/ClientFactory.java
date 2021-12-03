package com.io.rpcrefactor.rpc.transport;

import com.io.rpcrefactor.rpc.ResponseMappingCallback;
import com.io.rpcrefactor.rpc.protocol.MyContent;
import com.io.rpcrefactor.rpc.protocol.MyHeader;
import com.io.rpcrefactor.util.SerDerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ClientFactory {
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
    final ConcurrentHashMap<InetSocketAddress, ClientPool> outboxs = new ConcurrentHashMap<>();

    public NioSocketChannel getClient(InetSocketAddress address) {
        ClientPool clientPool = outboxs.get(address);
        if (clientPool == null) {
            synchronized (outboxs) {
                clientPool = outboxs.get(address);
                if (clientPool == null) {
                    outboxs.put(address, new ClientPool(poolSize));
                    clientPool = outboxs.get(address);
                }
            }
        }

        // 随机选一个
        int i = random.nextInt(poolSize);

        if (clientPool.clients[i] != null && clientPool.clients[i].isActive()) {
            return clientPool.clients[i];
        } else {
            synchronized (clientPool.lock[i]) {
                if (clientPool.clients[i] == null || !clientPool.clients[i].isActive()) {
                    clientPool.clients[i] = createClient(address);
                }
            }
        }

        return clientPool.clients[i];
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


    public static CompletableFuture<Object> transport(MyContent content) {
        //content  就是货物  现在可以用自定义的rpc传输协议（有状态），也可以用http协议作为载体传输
        //我们先手工用了http协议作为载体，那这样是不是代表我们未来可以让provider是一个tomcat  jetty 基于http协议的一个容器
        //有无状态来自于你使用的什么协议，那么http协议肯定是无状态，每请求对应一个连接
        //dubbo 是一个rpc框架  netty 是一个io框架
        //dubbo中传输协议上，可以是自定义的rpc传输协议，http协议

        // String type = "rpc";
        String type = "http";
        CompletableFuture<Object> res = new CompletableFuture<>();
        if ("rpc".equals(type)) {
            byte[] msgBody = SerDerUtil.ser(content);
            MyHeader header = MyHeader.createHeader(msgBody);
            byte[] msgHeader = SerDerUtil.ser(header);
            System.out.println("header length:　" + msgHeader.length);

            NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("127.0.0.1", 9090));

            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);

            long requestID = header.getRequestID();
            ResponseMappingCallback.addCallBack(requestID, res);
            byteBuf.writeBytes(msgHeader);
            byteBuf.writeBytes(msgBody);
            ChannelFuture channelFuture = clientChannel.writeAndFlush(byteBuf);
        } else {
            // 使用http协议作为载体
            // 1. 用URL 现成的工具（包含http的编解码，发送，socket，连接）
            urlTS(content, res);

            // 2. 自己操心： on netty （io框架） + 已经提供的http相关的编解码
            //nettyTs(content, res);
        }


        return res;
    }

    private static void urlTS(MyContent content, CompletableFuture<Object> res) {
        // 没请求占用一个连接，因为是http的请求方式
        Object obj = null;
        try {
            URL url = new URL("http://127.0.0.1:9090/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // post
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            OutputStream out = urlConnection.getOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(content);

            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();
                ObjectInputStream oin = new ObjectInputStream(in);
                MyContent o = (MyContent) oin.readObject();
                obj = o.getRes();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        res.complete(obj);
    }

    private static void nettyTs(MyContent content, CompletableFuture<Object> res) {
        //在这个执行之前  我们的server端 provider端已经开发完了，已经是on netty的http server了
        //现在做的事consumer端的代码修改，改成 on netty的http client
        //刚才一切都顺利，关注未来的问题。。。。。。

        //每个请求对应一个连接
        //1，通过netty建立io 建立连接
        //TODO  :  改成 多个http的request 复用一个 netty client，而且 并发发送请求
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        Bootstrap client = bs.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpClientCodec())
                                .addLast(new HttpObjectAggregator(1024*512))
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        // 3 接收
                                        FullHttpResponse response = (FullHttpResponse) msg;
                                        System.out.println(response.toString());

                                        ByteBuf resContent = response.content();
                                        byte[] data = new byte[resContent.readableBytes()];
                                        resContent.readBytes(data);

                                        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
                                        MyContent o = (MyContent) oin.readObject();

                                        res.complete(o.getRes());
                                    }
                                });
                    }
                });

        try {
            ChannelFuture channelFuture = client.connect("127.0.0.1", 9090).sync();
            Channel channel = channelFuture.channel();
            // 2 发送
            byte[] data = SerDerUtil.ser(content);
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0,
                    HttpMethod.POST, "/",
                    Unpooled.copiedBuffer(data));

            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, data.length);

            channel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}