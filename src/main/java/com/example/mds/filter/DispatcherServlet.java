package com.example.mds.filter;

import com.example.mds.annotation.RequestMapping;
import com.example.mds.controller.UserController;
import com.example.mds.entity.RequestMappingInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*
매번 요청 정보마다 Servlet을 생성하는 것은 비효율적이기 때문에
한 곳에서 요청을 전부 받은 후 요청을 분석하여 적절한 Servlet, Method를 호출해줍니다.
 */
@WebFilter(urlPatterns = "/*")
public class DispatcherServlet implements Filter {

    private final Map<String, RequestMappingInfo> mappingInfoMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /*
            MappingRegistry 구현
            초기화 시 먼저 RequestMapping 에 대한 정보를 저장해둡니다. (캐싱)
         */
        UserController userController = new UserController();
        Method[] methods = userController.getClass().getDeclaredMethods();

        for (Method method : methods) {
            RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);

            // 해당 메소드에 @RequestMapping 어노테이션이 없을 경우 Skip
            if (requestMapping == null) {
                continue;
            }

            // 이미 등록된 url 정보가 있을 경우 에러 발생
            if (mappingInfoMap.get(requestMapping.value()) != null) {
                throw new RuntimeException("Already Exists Request Mapping!!!!");
            }
            mappingInfoMap.put(requestMapping.value(), new RequestMappingInfo(userController, method));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Start Dispatcher");

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        System.out.println("Context Path : " + servletRequest.getContextPath());
        System.out.println("Request URI : " + servletRequest.getRequestURI());
        System.out.println("Request URL : " + servletRequest.getRequestURL());

        /*
        컨텍스트 패스가 없을 경우는 RequestURI 로 요청 정보를 분석해도 되지만
        컨텍스트 패스가 있을 경우는 컨텍스트 패스를 제거해줘야 합니다.
         */

        String endPoint = servletRequest.getRequestURI().replaceAll(servletRequest.getContextPath(), "");
        System.out.println("endPoint : " + endPoint);

        RequestMappingInfo requestMappingInfo = mappingInfoMap.get(endPoint);

        // 요청에 대한 매핑 정보가 없을 경우 에러 발생
        if (requestMappingInfo == null) {
            throw new RuntimeException("404 Not Found Exception : " + endPoint);
        }

        Object target = requestMappingInfo.getTarget();
        Method method = requestMappingInfo.getMethod();

        try {
            method.invoke(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void destroy() {

    }

}
