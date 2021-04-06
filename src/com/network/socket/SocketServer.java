package com.tcp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @decription: socket服务端
 * @author: 180449
 * @date 2021/3/1 13:53
 */
public class SocketServer {
    public static void main(String[] args) {
        ServerSocket ss = null;
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        // 监听端口号
        int port = 10001;
        try {
            // 建立连接 ，监听端口
            ss = new ServerSocket(port);
            // 获得连接
            socket = ss.accept();
            // 接收内容
            is = socket.getInputStream();
            byte[] b = new byte[1024];
            int n = is.read(b);
            // 输出
            System.out.println("客户端发送内容为：" +  new String(b, 0, n));

            // 向客户端发送响应内容
            os = socket.getOutputStream();
            os.write(b, 0, n);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                socket.close();
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
