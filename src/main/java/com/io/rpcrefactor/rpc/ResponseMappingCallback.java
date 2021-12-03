package com.io.rpcrefactor.rpc;

import com.io.rpcrefactor.util.Packmsg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseMappingCallback {

    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestID, CompletableFuture cb) {
        mapping.put(requestID, cb);
    }

    public static void runCallBack(Packmsg packmsg) {
        long requestID = packmsg.getHeader().getRequestID();
        CompletableFuture cb = mapping.get(requestID);
        cb.complete(packmsg.getContent().getRes());
        removeCB(requestID);
    }

    public static void removeCB(long requestID) {
        mapping.remove(requestID);
    }

}