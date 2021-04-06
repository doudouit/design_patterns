package com.tcp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @decription: 服务器端逻辑线程
 * @author: 180449
 * @date 2021/3/1 14:54
 */
public class LogicThread extends Thread{

    Socket socket = null;
    InputStream is = null;
    OutputStream os = null;

    public LogicThread(Socket socket) {
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        byte[] b = new byte[1024];
        try {
            // 初始化流
            os = socket.getOutputStream();
            is = socket.getInputStream();
            // 读取数据
            int n = is.read(b);
            // 逻辑处理
            byte[] response = logic(b, 0, n);
            // 反馈数据
            os.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private byte[] logic(byte[] b, int i, int n) {
        byte[] response = new byte[n];
        // 将有效数据拷贝到response中
        System.arraycopy(b, 0, response, 0, n);
        return response;
    }

    public void close() {
        try {
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
