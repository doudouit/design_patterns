package com.io.rpcrefactor.rpc.transport;

import com.io.rpcrefactor.rpc.protocol.MyContent;
import com.io.rpcrefactor.rpc.protocol.MyHeader;
import com.io.rpcrefactor.util.Packmsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class ServerDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        while(buf.readableBytes() >= 110) {
            byte[] bytes = new byte[110];
            buf.getBytes(buf.readerIndex(),bytes);  //从哪里读取，读多少，但是readindex不变
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(in);
            MyHeader header = (MyHeader) oin.readObject();


            //DECODE在2个方向都使用
            //通信的协议
            if(buf.readableBytes() >= header.getDataLen()){
                //处理指针
                buf.readBytes(110);  //移动指针到body开始的位置
                byte[] data = new byte[(int)header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);

                if(header.getFlag() == 0x14141414){
                    MyContent content = (MyContent) doin.readObject();
                    out.add(new Packmsg(header,content));

                }else if(header.getFlag() == 0x14141424){
                    MyContent content = (MyContent) doin.readObject();
                    out.add(new Packmsg(header,content));
                }
            }else{
                break;
            }

        }

    }
}