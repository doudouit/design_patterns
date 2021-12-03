package com.io.rpcrefactor.rpc.protocol;

import java.io.Serializable;
import java.util.UUID;

public class MyHeader implements Serializable {

    private static final long serialVersionUID = -8874173164157264719L;

    //通信上的协议
    /*
    1，ooxx值
    2，UUID:requestID
    3，DATA_LEN

     */
    int flag;  //32bit可以设置很多信息。。。
    long requestID;
    long dataLen;

    public MyHeader() {
    }

    public MyHeader(int flag, long requestID, long dataLen) {
        this.flag = flag;
        this.requestID = requestID;
        this.dataLen = dataLen;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public long getDataLen() {
        return dataLen;
    }

    public void setDataLen(long dataLen) {
        this.dataLen = dataLen;
    }


    public static MyHeader createHeader(byte[] msg) {
        int flag = 0x14141414;
        int size = msg.length;
        long requestID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        return new MyHeader(flag, requestID, size);
    }

}