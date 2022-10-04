package com.example.mds.entity;

import java.lang.reflect.Method;

public class RequestMappingInfo {

    private Object target;
    private Method method;

    public RequestMappingInfo(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
