package com.network.datagram;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @decription: UDP服务端
 * @author: 180449
 * @date 2021/3/1 16:20
 */
public class SimpleDUPServer {
    public static void main(String[] args) {
        DatagramSocket datagramSocket = null;
        DatagramPacket datagramPacketSend;
        DatagramPacket datagramPacketReceive;
        int port = 10010;
        try {
            // 建立连接、监听端口
            datagramSocket = new DatagramSocket(port);
            System.out.println("服务器端已启动");

            // 初始化接收数据
            byte[] b = new byte[1024];
            datagramPacketReceive = new DatagramPacket(b, b.length);
            datagramSocket.receive(datagramPacketReceive);

            // 读取反馈内容并输出
            InetAddress clientIp = datagramPacketReceive.getAddress();
            int clientPort = datagramPacketReceive.getPort();
            byte[] data = datagramPacketReceive.getData();
            int length = datagramPacketReceive.getLength();
            System.out.println("客户端ip：" + clientIp.getHostAddress());
            System.out.println("客户端端口：" + clientPort);
            System.out.println("客户端发送内容：" + new String(data,0, length));

            // 反馈内容
            String response = "OK";
            byte[] responseBytes = response.getBytes();
            datagramPacketSend = new DatagramPacket(responseBytes, responseBytes.length, clientIp, clientPort);
            datagramSocket.send(datagramPacketSend);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                datagramSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
