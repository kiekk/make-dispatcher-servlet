package com.example.mds.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    }

    @Override
    public void destroy() {

    }

}
