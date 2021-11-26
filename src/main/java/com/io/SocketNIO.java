package com.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @decription: NIO
 * @author: 180449
 * @date 2021/11/26 14:40
 */
public class SocketNIO {

    public static void main(String[] args) throws Exception {

        LinkedList<SocketChannel> clients = new LinkedList<>();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9090));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        while (true) {
            // 接受客户端连接
            Thread.sleep(1000);
            // 不会阻塞，没有就返回-1
            SocketChannel client = serverSocketChannel.accept();

            if (client != null) {
                // socket（服务端的listen socket<连接请求三次握手后，往我这里扔，我去通过accept 得到  连接的socket>，连接socket<连接后的数据读写使用的> ）
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client..port:" + port);
                clients.add(client);
            }

            // 堆外空间
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);

            // 遍历镀金来的客户端能不能读写数据
            for (SocketChannel c : clients) {
                // > 0 -1 0 不会阻塞
                int num = c.read(byteBuffer);
                if (num > 0) {
                    byteBuffer.flip();
                    byte[] aaa = new byte[byteBuffer.limit()];
                    byteBuffer.get(aaa);

                    String b = new String(aaa);
                    System.out.println(c.socket().getPort() + " : " + b);
                    byteBuffer.clear();
                }
            }
        }
    }
}
