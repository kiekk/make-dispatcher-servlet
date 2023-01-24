package com.example.mds;

import com.example.mds.entity.MappingRegistry;
import com.example.mds.entity.RequestHandlerAdapter;
import com.example.mds.entity.RequestMappingInfo;
import com.example.mds.resolver.Resolver;
import com.example.mds.resolver.ResolverFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

/*
매번 요청 정보마다 Servlet을 생성하는 것은 비효율적이기 때문에
한 곳에서 요청을 전부 받은 후 요청을 분석하여 적절한 Servlet, Method를 호출해줍니다.
 */
@WebFilter(urlPatterns = "/*")
public class DispatcherServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // DispatcherServlet 을 상위 패키지에 위치
        // DispatcherServlet 하위 패키지를 모두 스캔
        MappingRegistry.handlerMapping(this.getClass().getPackageName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        System.out.println("Start Dispatcher");

        RequestMappingInfo requestMappingInfo = MappingRegistry.getHandler(request);

        try {
            Object invoke = RequestHandlerAdapter.handle(requestMappingInfo);
            Resolver resolver = ResolverFactory.getResolver(requestMappingInfo);
            resolver.resolve(request, response, invoke);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void destroy() {

    }

}
