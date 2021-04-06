package com.tcp.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @decription: 支持多客户端的服务端实现
 * @author: 180449
 * @date 2021/3/1 14:49
 */
public class MulThreadSocketServer {
    public static void main(String[] args) {
        ServerSocket socketServer = null;
        Socket socket = null;
        // 监听端口好
        int port = 10001;
        try {
            socketServer = new ServerSocket(port);
            System.out.println("服务端已启动！");
            while (true) {
                // 获得连接
                socket = socketServer.accept();
                // 启动线程
                new LogicThread(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
