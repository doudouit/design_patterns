package com.io.rpcrefactor.proxy;

import com.io.rpcrefactor.rpc.Dispatcher;
import com.io.rpcrefactor.rpc.protocol.MyContent;
import com.io.rpcrefactor.rpc.transport.ClientFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;

/**
 * @decription: 代理类
 * @author: 180449
 * @date 2021/12/3 8:52
 */
public class MyProxy {

    public static <T> T proxyGet(Class<T> interfaceInfo) {
        // 实现动态代理

        ClassLoader loader = interfaceInfo.getClassLoader();
        Class<?>[] methedInfo = {interfaceInfo};

        Dispatcher dis = Dispatcher.getDis();

        return (T) Proxy.newProxyInstance(loader, methedInfo, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                Object o = dis.get(interfaceInfo.getName());
                if (o != null) {
                    // 本地rpc调用
                    System.out.println("local FC");
                    Class<?> clazz = o.getClass();
                    try {
                        Method m = clazz.getMethod(method.getName(), method.getParameterTypes());
                        result = m.invoke(o, args);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } else {
                    // 远程rpc
                    String name = interfaceInfo.getName();
                    String methodName = method.getName();
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    MyContent conetnt = new MyContent(name, methodName, parameterTypes, args);

                    CompletableFuture<Object> res = ClientFactory.transport(conetnt);

                    // 阻塞
                    result = res.get();
                }
                return result;
            }
        });
    }
}
