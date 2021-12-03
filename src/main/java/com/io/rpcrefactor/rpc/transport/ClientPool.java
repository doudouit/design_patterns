package com.io.rpcrefactor.rpc.transport;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端连接池
 */
class ClientPool {
    NioSocketChannel[] clients;
    // 伴生锁
    Object[] lock;

    ClientPool(int size) {
        // init 连接都是空的
        clients = new NioSocketChannel[size];
        // 锁是可以初始化的
        lock = new Object[size];
        for (int i = 0; i < size; i++) {
            lock[i] = new Object();
        }
    }
}