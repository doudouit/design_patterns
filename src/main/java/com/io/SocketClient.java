package com.io;

import java.io.*;
import java.net.Socket;

/**
 * @decription: 客户端
 * @author: 180449
 * @date 2021/11/26 10:42
 */
public class SocketClient {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 9090);

            client.setSendBufferSize(20);
            client.setTcpNoDelay(true);
            OutputStream outputStream = client.getOutputStream();

            // 输入字符
            InputStream in = System.in;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (true) {
                String readLine = reader.readLine();
                if (readLine != null) {
                    byte[] bytes = readLine.getBytes();
                    for (byte b : bytes) {
                        outputStream.write(b);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
