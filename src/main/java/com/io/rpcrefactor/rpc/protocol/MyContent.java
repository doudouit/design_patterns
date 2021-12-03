package com.io.rpcrefactor.rpc.protocol;

import java.io.Serializable;

public class MyContent implements Serializable {
    private static final long serialVersionUID = 3622067262756634471L;

    private String name;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] args;
    private Object res;

    public MyContent() {
    }

    public MyContent(String name, String methodName, Class<?>[] parameterTypes, Object[] args) {
        this.name = name;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.args = args;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}