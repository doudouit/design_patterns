package com.io.rpcrefactor.rpc.transport;

import com.io.rpcrefactor.rpc.ResponseMappingCallback;
import com.io.rpcrefactor.rpc.protocol.MyContent;
import com.io.rpcrefactor.rpc.protocol.MyHeader;
import com.io.rpcrefactor.util.SerDerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
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
        byte[] msgBody = SerDerUtil.ser(content);
        MyHeader header = MyHeader.createHeader(msgBody);
        byte[] msgHeader = SerDerUtil.ser(header);
        System.out.println("header length:　" + msgHeader.length);

        NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("127.0.0.1", 9090));

        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);

        long requestID = header.getRequestID();
        CompletableFuture<Object> res = new CompletableFuture<>();
        ResponseMappingCallback.addCallBack(requestID, res);
        byteBuf.writeBytes(msgHeader);
        byteBuf.writeBytes(msgBody);
        ChannelFuture channelFuture = clientChannel.writeAndFlush(byteBuf);

        return res;
    }

}