package com.example.mds.filter;

import com.example.mds.controller.UserController;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
매번 요청 정보마다 Servlet을 생성하는 것은 비효율적이기 때문에
한 곳에서 요청을 전부 받은 후 요청을 분석하여 적절한 Servlet, Method를 호출해줍니다.
 */
@WebFilter(urlPatterns = "/*")
public class DispatcherServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

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

        UserController userController = new UserController();
        Method[] methods = userController.getClass().getDeclaredMethods();
        // getMethods() : 상속된 메소드도 전부 가져옴
        // getDeclaredMethods() : 해당 객체의 메소드만 가져옴

        // Reflection 으로 UserController 의 메소드 정보를 찾아 url 정보와 매핑
        for (Method method : methods) {
            if (endPoint.equals("/" + method.getName())) {
                try {
                    method.invoke(userController);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

}
