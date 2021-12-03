package com.io.rpcrefactor.rpc.transport;

import com.io.rpcrefactor.rpc.Dispatcher;
import com.io.rpcrefactor.rpc.protocol.MyContent;
import com.io.rpcrefactor.rpc.protocol.MyHeader;
import com.io.rpcrefactor.util.Packmsg;
import com.io.rpcrefactor.util.SerDerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    private Dispatcher dis;

    public ServerRequestHandler(Dispatcher dis) {
        this.dis = dis;
    }

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
        //ctx.executor().parent().next().execute(() -> {

            String serviceName = requestPkg.getContent().getName();
            String methodName = requestPkg.getContent().getMethodName();
            Object c = dis.get(serviceName);
            Class<?> clazz = c.getClass();
            Object res = null;

            try {
                Method method = clazz.getMethod(methodName, requestPkg.getContent().getParameterTypes());
                res = method.invoke(c, requestPkg.getContent().getArgs());

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            String execThreadName = Thread.currentThread().getName();
            MyContent content = new MyContent();
            //String res = "io thread: " + ioThreadName + "exec Thread: " + execThreadName + " from args: " + arg;
            content.setRes(res);
            byte[] contentByte = SerDerUtil.ser(content);

            MyHeader resHeader = new MyHeader();
            resHeader.setRequestID(requestPkg.getHeader().getRequestID());
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