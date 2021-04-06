package com.tcp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @decription: socket客户端
 * @author: 180449
 * @date 2021/3/1 13:46
 */
public class SocketClient {
    public static void main(String[] args) {
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        String host = "127.0.0.1";
        int port = 10001;
        // 发送内容
        String data = "hello";

        try {
            // 建立连接
            socket = new Socket(host, port);
            // 发送数据
            os = socket.getOutputStream();
            os.write(data.getBytes());
            // 接收数据
            is = socket.getInputStream();
            byte[] b = new byte[1024];
            int n = is.read(b);
            // 输出反馈的数据
            System.out.println("反馈：" + new String(b, 0, n));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
