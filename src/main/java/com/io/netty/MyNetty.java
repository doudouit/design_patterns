package com.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @decription: 自定义netty
 * @author: 180449
 * @date 2021/11/29 16:45
 */
public class MyNetty {

    /*
        目的：前边 NIO 逻辑
        恶心的版本---依托着前面的思维逻辑
        channel  bytebuffer  selector
        bytebuffer   bytebuf【pool】
     */

    @Test
    public void testBuffer() {
        //ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(8, 20);
        //ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        print(buf);

        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
    }

    public static void print(ByteBuf buf) {
        System.out.println("buf.isReadable()    :" + buf.isReadable());
        System.out.println("buf.readerIndex()   :" + buf.readerIndex());
        System.out.println("buf.readableBytes() " + buf.readableBytes());
        System.out.println("buf.isWritable()    :" + buf.isWritable());
        System.out.println("buf.writerIndex()   :" + buf.writerIndex());
        System.out.println("buf.writableBytes() :" + buf.writableBytes());
        System.out.println("buf.capacity()  :" + buf.capacity());
        System.out.println("buf.maxCapacity()   :" + buf.maxCapacity());
        System.out.println("buf.isDirect()  :" + buf.isDirect());
        System.out.println("--------------");
    }

    /**
     * 客户端
     * 连接别人
     * 1，主动发送数据
     * 2，别人什么时候给我发？  event  selector
     */
    @Test
    public void loopExecutor() throws IOException {
        // 指定线程数
        NioEventLoopGroup selector = new NioEventLoopGroup(2);
        selector.execute(() -> {
            try {
                for (; ; ) {
                    System.out.println("helloworld1");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        selector.execute(() -> {
            try {
                for (; ; ) {
                    System.out.println("helloworld2");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        System.in.read();
    }


    @Test
    public void clientMode() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);

        // 客户端模式
        NioSocketChannel client = new NioSocketChannel();
        //epoll_ctl(5,ADD,3)
        group.register(client);


        // 响应式
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new MyInHandler());

        // reactor  异步的特征
        ChannelFuture connect = client.connect(new InetSocketAddress("127.0.0.1", 9090));
        ChannelFuture sync = connect.sync();


        ByteBuf buf = Unpooled.copiedBuffer("hello world".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        sync.channel().closeFuture().sync();

        System.out.println("client over");


    }


    @Test
    public void nettyClient() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture connect = bootstrap.group(group)
                .channel(NioSocketChannel.class)
//                .handler(new ChannelInit())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MyInHandler());
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 9090));

        Channel client = connect.sync().channel();

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world".getBytes());
        ChannelFuture send = client.writeAndFlush(byteBuf);
        send.sync();

        client.closeFuture().sync();
    }

    @Test
    public void serverMode() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        NioServerSocketChannel server = new NioServerSocketChannel();

        group.register(server);

        ChannelPipeline pipeline = server.pipeline();
        pipeline.addLast(new MyAcceptHandler(group, new ChannelInit()));

        // accept接收客户端，并且注册到selector
        ChannelFuture bind = server.bind(new InetSocketAddress("127.0.0.1", 9090));
        bind.sync().channel().closeFuture().sync();
        System.out.println("server close.......");
    }


    @Test
    public void nettyServer() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bs = new ServerBootstrap();

        ChannelFuture bind = bs.group(group, group)
                .channel(NioServerSocketChannel.class)
                //.childHandler(new ChannelInit())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MyInHandler());
                    }
                })
                .bind(new InetSocketAddress("127.0.0.1", 9090));

        bind.sync().channel().closeFuture().sync();

    }

}

class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    public MyAcceptHandler(NioEventLoopGroup group, ChannelHandler channelHandler) {
        this.selector = group;
        this.handler = channelHandler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server registered....");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // listen socket accpet client
        // socket                R/W
        SocketChannel client = (SocketChannel) msg;
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(handler);

        selector.register(client);
    }
}

//为啥要有一个inithandler，可以没有，但是MyInHandler就得设计成单例
@ChannelHandler.Sharable
class ChannelInit extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        ChannelPipeline p = client.pipeline();
        p.addLast(new MyInHandler());//2,client::pipeline[ChannelInit,MyInHandler]
        ctx.pipeline().remove(this);
        //3,client::pipeline[MyInHandler]
    }

}

/*
就是用户自己实现的，你能说让用户放弃属性的操作吗
@ChannelHandler.Sharable  不应该被强压给coder
 */
class MyInHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client  registed...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        // 指针移动
        // CharSequence str = buf.readCharSequence(buf.readableBytes(),  CharsetUtil.UTF_8);
        // 指针不移动
        CharSequence str = buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(str);
        ctx.writeAndFlush(buf);
    }
}
