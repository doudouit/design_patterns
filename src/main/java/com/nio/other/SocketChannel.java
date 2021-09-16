package com.nio.other;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @decription:
 * @author: 180449
 * @date 2021/9/16 14:43
 */
public class SocketChannel {

    public static void main(String[] args) {
        // method1();
        method2();
    }

    /**
     * FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中。
     */
    public static void method1() {
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try {
            fromFile = new RandomAccessFile("src/nio.txt", "rw");
            FileChannel fromChannel = fromFile.getChannel();
            toFile = new RandomAccessFile("src/toFile.txt", "rw");
            FileChannel toChannel = toFile.getChannel();
            long position = 0;
            long count = fromChannel.size();
            System.out.println(count);
            toChannel.transferFrom(fromChannel, position, count);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fromFile != null) {
                    fromFile.close();
                }
                if (toFile != null) {
                    toFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * transferTo()方法将数据从FileChannel传输到其他的channel中
     */
    public static void method2() {
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try {
            fromFile = new RandomAccessFile("src/nio.txt", "rw");
            FileChannel fromChannel = fromFile.getChannel();
            toFile = new RandomAccessFile("src/toFile.txt", "rw");
            FileChannel toChannel = toFile.getChannel();
            long position = 0;
            long count = fromChannel.size();
            System.out.println(count);
            fromChannel.transferTo(position, count, toChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fromFile != null) {
                    fromFile.close();
                }
                if (toFile != null) {
                    toFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
