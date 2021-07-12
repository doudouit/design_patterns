package com.network.datagram;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

/**
 * @decription: UDP客户端
 * @author: 180449
 * @date 2021/3/1 16:20
 */
public class SimpleUDPClient {

    public static void main(String[] args) {
        // 连接对象
        DatagramSocket datagramSocket = null;
        // 发送数据包对象
        DatagramPacket datagramPacketSend;
        // 接收数据包对象
        DatagramPacket datagramPacketReceive;
        String host = "127.0.0.1";
        int port = 10010;
        try {
            // 建立连接
            datagramSocket = new DatagramSocket();
            // 发送的数据
            Date date = new Date();
            String content = date.toString();
            byte[] data = content.getBytes();

            // 初始化发送包对象
            InetAddress address = InetAddress.getByName(host);
            datagramPacketSend = new DatagramPacket(data, data.length, address, port);
            // 发送
            datagramSocket.send(datagramPacketSend);

            // 初始化接收数据
            byte[] b = new byte[1024];
            datagramPacketReceive = new DatagramPacket(b, b.length);
            datagramSocket.receive(datagramPacketReceive);
            // 读取反馈内容并输出
            byte[] response = datagramPacketReceive.getData();
            int length = datagramPacketReceive.getLength();
            String s = new String(response, 0, length);
            System.out.println("服务器反馈为：" + s);
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
