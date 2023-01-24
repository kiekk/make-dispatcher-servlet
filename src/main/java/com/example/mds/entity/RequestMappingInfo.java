package com.example.mds.entity;

import com.example.mds.annotation.ResponseBody;

import java.lang.reflect.Method;

public class RequestMappingInfo {

    private Object target;
    private Method method;
    private boolean isRestController;

    public RequestMappingInfo(Object target, Method method) {
        this.target = target;
        this.method = method;
        this.isRestController = target.getClass().isAnnotationPresent(ResponseBody.class) || method.isAnnotationPresent(ResponseBody.class);
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

    public boolean isRestController() {
        return isRestController;
    }

    public void setRestController(boolean restController) {
        isRestController = restController;
    }
}
