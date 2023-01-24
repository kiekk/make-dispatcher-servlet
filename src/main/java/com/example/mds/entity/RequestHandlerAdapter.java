package com.example.mds.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandlerAdapter {

    public static Object handle(RequestMappingInfo mappingInfo) throws InvocationTargetException, IllegalAccessException {

        Object target = mappingInfo.getTarget();
        Method method = mappingInfo.getMethod();

        return method.invoke(target);
    }
}
