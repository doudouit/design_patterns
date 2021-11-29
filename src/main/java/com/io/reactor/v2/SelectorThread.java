package com.io.reactor.v2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @decription: select线程
 * @author: 180449
 * @date 2021/11/29 14:10
 */
public class SelectorThread implements Runnable{

    /**
     * 每线程对应一个selector
     */
    Selector selector = null;
    SelectorThreadGroup stg;

    LinkedBlockingQueue<Channel> lbq = new LinkedBlockingQueue<>();

    SelectorThread(SelectorThreadGroup sts) {
        try {
            this.stg = sts;
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 阻塞  wakeup()
                int nums = selector.select();

                if (nums > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            // 复杂,接受客户端的过程（接收之后，要注册，多线程下，新的客户端，注册到那里呢？）
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        } else if (key.isWritable()) {

                        }
                     }
                }

                if (!lbq.isEmpty()) {
                    Channel c = lbq.take();
                    if (c instanceof ServerSocketChannel) {
                        ServerSocketChannel server = (ServerSocketChannel) c;
                        server.register(selector,SelectionKey.OP_ACCEPT);
                        System.out.println(Thread.currentThread().getName()+" register listen");
                    } else if (c instanceof SocketChannel) {
                        SocketChannel client = (SocketChannel) c;
                        ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                        client.register(selector, SelectionKey.OP_READ, buffer);
                        System.out.println(Thread.currentThread().getName()+" register client: " + client.getRemoteAddress());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void acceptHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName()+"   acceptHandler......");

        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);

            //  change a selector and register
            stg.nextSelectorV2(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName()+" read......");
        ByteBuffer buffer = (ByteBuffer)key.attachment();
        SocketChannel client = (SocketChannel)key.channel();
        buffer.clear();
        while(true){
            try {
                int num = client.read(buffer);
                if(num > 0){
                    buffer.flip();  //将读到的内容翻转，然后直接写出
                    while(buffer.hasRemaining()){
                        client.write(buffer);
                    }
                    buffer.clear();
                }else if(num == 0){
                    break;
                }else if(num < 0 ){
                    //客户端断开了
                    System.out.println("client: " + client.getRemoteAddress()+"closed......");
                    key.cancel();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
