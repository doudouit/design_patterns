package com.io.rpcrefactor.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @decription: 字节码工具类
 * @author: 180449
 * @date 2021/12/2 9:49
 */
public class SerDerUtil {

    static ByteArrayOutputStream out = new ByteArrayOutputStream();

    public synchronized static byte[] ser (Object msg) {
        out.reset();
        ObjectOutputStream oout = null;
        byte[] msgBody = null;
        try {
            oout = new ObjectOutputStream(out);
            oout.writeObject(msg);
            msgBody = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msgBody;
    }
}
