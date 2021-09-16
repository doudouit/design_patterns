package com.nio.other;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScattingAndGather {

    public static void main(String args[]) {
        gather();
    }


    /**
     * 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
     * <p>
     * 聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
     */
    public static void gather() {
        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);
        byte[] b1 = {'0', '1'};
        byte[] b2 = {'2', '3'};
        header.put(b1);
        body.put(b2);
        ByteBuffer[] buffs = {header, body};
        try {
            FileOutputStream os = new FileOutputStream("src/scattingAndGather.txt");
            FileChannel channel = os.getChannel();
            channel.write(buffs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
