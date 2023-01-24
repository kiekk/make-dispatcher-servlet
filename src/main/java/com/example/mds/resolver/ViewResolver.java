package com.example.mds.resolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewResolver implements Resolver {

    private final String REDIRECT_PREFIX = "redirect:";
    private final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private final String VIEW_RESOLVER_SUFFIX = ".jsp";

    @Override
    public void resolve(ServletRequest request, ServletResponse response, Object result) throws ServletException, IOException {
// ViewResolver
        System.out.println("ViewResolver로 처리된 결과 : " + result);

        String resultStr = String.valueOf(result);

        // Redirect 여부 확인
        if (resultStr.startsWith(REDIRECT_PREFIX)) {
            // Redirect
            resultStr = resultStr.replaceAll(REDIRECT_PREFIX, "");
            ((HttpServletResponse) response).sendRedirect(resultStr);
        } else {
            // Forward
            RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_RESOLVER_PREFIX + resultStr + VIEW_RESOLVER_SUFFIX);
            dispatcher.forward(request, response);
        }
    }
}
