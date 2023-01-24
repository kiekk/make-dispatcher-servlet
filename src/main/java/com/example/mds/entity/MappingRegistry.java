package com.example.mds.entity;

import com.example.mds.annotation.Controller;
import com.example.mds.annotation.RequestMapping;
import com.example.mds.util.ClassLoaderUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MappingRegistry {
    private static final Map<String, RequestMappingInfo> pathLookup = new HashMap<>();

    public static RequestMappingInfo getHandler(String endPoint) {
        RequestMappingInfo requestMappingInfo = pathLookup.get(endPoint);

        // 요청에 대한 매핑 정보가 없을 경우 에러 발생
        if (requestMappingInfo == null) {
            throw new RuntimeException("404 Not Found Exception : " + endPoint);
        }

        return requestMappingInfo;
    }

    public static void handlerMapping(String basePackage) {

        /*
            ComponentScan 구현
            스캔할 package 명을 전달받아 하위에 있는 class를 전부 찾습니다.
            그리고 해당 class에 @Controller 어노테이션이 있는 class만 매핑 작업을 합니다.
         */
        Class[] classes;
        try {
            classes = ClassLoaderUtil.getClasses(basePackage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (classes.length == 0) {
            throw new RuntimeException("Classes Empty!!");
        }

        for (Class targetClass : classes) {
            // @Controller 어노테이션 여부 확인
            boolean isController = targetClass.isAnnotationPresent(Controller.class);

            if (!isController) {
                continue;
            }

            RequestMapping classRequestMapping = (RequestMapping) targetClass.getDeclaredAnnotation(RequestMapping.class);

            // Controller의 @RequestMapping과 method의 @ReuqestMapping을 합치기 위한 변수
            String mappingUrlInfo = null;
            if (classRequestMapping != null) {
                mappingUrlInfo = classRequestMapping.value();
            }


            Method[] methods = targetClass.getDeclaredMethods();

            // 인스턴스화
            Object instance;
            try {
                instance = targetClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            /*
                MappingRegistry 구현
                초기화 시 먼저 RequestMapping 에 대한 정보를 저장해둡니다. (캐싱)
             */

            for (Method method : methods) {
                RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);

                // 해당 메소드에 @RequestMapping 어노테이션이 없을 경우 Skip
                if (requestMapping == null) {
                    continue;
                }

                // 이미 등록된 url 정보가 있을 경우 에러 발생
                if (pathLookup.get(mappingUrlInfo + requestMapping.value()) != null) {
                    throw new RuntimeException("Already Exists Request Mapping!!!!");
                }
                pathLookup.put(mappingUrlInfo + requestMapping.value(), new RequestMappingInfo(instance, method));
            }
        }
    }
}
