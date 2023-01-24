package com.example.mds.filter;

import com.example.mds.annotation.ResponseBody;
import com.example.mds.entity.MappingRegistry;
import com.example.mds.entity.RequestHandlerAdapter;
import com.example.mds.entity.RequestMappingInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/*
매번 요청 정보마다 Servlet을 생성하는 것은 비효율적이기 때문에
한 곳에서 요청을 전부 받은 후 요청을 분석하여 적절한 Servlet, Method를 호출해줍니다.
 */
@WebFilter(urlPatterns = "/*")
public class DispatcherServlet implements Filter {

    private final String basePackage = "com.example.mds.controller";
    private final String REDIRECT_PREFIX = "redirect:";
    private final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private final String VIEW_RESOLVER_SUFFIX = ".jsp";

    @Override
    public void init(FilterConfig filterConfig) {
        MappingRegistry.handlerMapping(basePackage);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        System.out.println("Start Dispatcher");

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        System.out.println("Context Path : " + servletRequest.getContextPath());
        System.out.println("Request URI : " + servletRequest.getRequestURI());
        System.out.println("Request URL : " + servletRequest.getRequestURL());

        RequestMappingInfo requestMappingInfo = MappingRegistry.getHandler(servletRequest);

        Object target = requestMappingInfo.getTarget();
        Method method = requestMappingInfo.getMethod();

        try {
            Object invoke = RequestHandlerAdapter.handle(requestMappingInfo);

            // @ResponseBody 가 있을 경우 MessageConverter로 처리, 없을 경우 ViewResolver로 처리
            // MessageConverter
            if (target.getClass().isAnnotationPresent(ResponseBody.class) || method.isAnnotationPresent(ResponseBody.class)) {
                System.out.println("MessageConverter로 처리된 결과 : " + invoke);

                PrintWriter writer = response.getWriter();
                writer.println(invoke);
                writer.close();
            } else {
                // ViewResolver
                System.out.println("ViewResolver로 처리된 결과 : " + invoke);

                String result = String.valueOf(invoke);

                // Redirect 여부 확인
                if (result.startsWith(REDIRECT_PREFIX)) {
                    // Redirect
                    result = result.replaceAll(REDIRECT_PREFIX, "");
                    servletResponse.sendRedirect(result);
                } else {
                    // Forward
                    RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_RESOLVER_PREFIX + result + VIEW_RESOLVER_SUFFIX);
                    dispatcher.forward(servletRequest, servletResponse);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void destroy() {

    }

}
