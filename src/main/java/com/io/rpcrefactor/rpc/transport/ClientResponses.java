package com.io.rpcrefactor.rpc.transport;

import com.io.rpcrefactor.rpc.ResponseMappingCallback;
import com.io.rpcrefactor.util.Packmsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientResponses extends ChannelInboundHandlerAdapter {

    // consumer
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packmsg responsePkg = (Packmsg) msg;

        ResponseMappingCallback.runCallBack(responsePkg);

    }
}