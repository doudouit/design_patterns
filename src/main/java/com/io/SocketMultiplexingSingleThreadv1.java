package com.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @decription: 多路复用器单线程版
 * @author: 180449
 * @date 2021/11/26 14:59
 */
public class SocketMultiplexingSingleThreadv1 {

    private ServerSocketChannel server = null;
    private Selector selector = null;
    private int port = 9090;

    public void initServer() {

        try {
            // fd4
            server = ServerSocketChannel.open();
            // false 非阻塞  true阻塞
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // open --> epoll_create --> fd3
            // select poll epoll 优先选择epoll 但是可以通过-D修正，windows不支持epoll
            selector = Selector.open();

            /*
            register:
            select poll : jvm开辟一个数组 fd4 放进去
            epoll :　epoll_ctl(fd3, add, fd4, EPOLLIN
             */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。");
        try {
            while (true) {
                Set<SelectionKey> keys = selector.selectedKeys();
                System.out.println("size : " + keys.size());

                /*
                    select() , 可以设置参数： 正数，阻塞多长时间；0 无参数 阻塞
                    1. select poll : 内核 select(fd4) poll(fd4)
                    2. epoll ： 内核 epoll_wait()
                 */
                while (selector.select() > 0) {
                    // 返回有状态的fd集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();

                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // ？？？不移除会重复循环处理
                        iterator.remove();
                        if (key.isAcceptable()) {
                            //select，poll，因为他们内核没有空间，那么在jvm中保存和前边的fd4那个listen的一起
                            //epoll： 我们希望通过epoll_ctl把新的客户端fd注册到内核空间
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            //在当前线程，这个方法可能会阻塞  ，如果阻塞了十年，其他的IO早就没电了。。。
                            //所以，为什么提出了 IO THREADS
                            readHander(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readHander(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    // 读到东西了
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            // 获取到文件描述符 --> fd7
            SocketChannel client = server.accept();
            client.configureBlocking(false);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            /*
                select poll : jvm开辟一个数组fd7放进去
                epoll： epoll_ctl(fd3, ADD, fd7, EPOLLIN
             */
            client.register(selector, SelectionKey.OP_READ, byteBuffer);
            System.out.println("------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SocketMultiplexingSingleThreadv1 socketMultiplexingSingleThreadv1 = new SocketMultiplexingSingleThreadv1();
        socketMultiplexingSingleThreadv1.start();
    }
}
