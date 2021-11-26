package com.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @decription: BIO
 * @author: 180449
 * @date 2021/11/26 10:26
 */
public class SocketIO {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090, 20);
        System.out.println("step1 : new ServerSocket(9090)");

        while (true) {
            // 阻塞1
            Socket client = serverSocket.accept();
            System.out.println("step2: client" + client.getPort());
            //new Thread(() -> {
                InputStream in = null;
                try {
                    in = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    while (true) {
                        // 阻塞2
                        String readLine = reader.readLine();
                        if (readLine != null) {
                            System.out.println(readLine);
                        } else {
                            client.close();
                            break;
                        }
                    }
                    System.out.println("端口号:" + client.getPort() + "客户端断开");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //});
        }
    }
}
