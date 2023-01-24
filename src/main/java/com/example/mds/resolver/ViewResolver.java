package com.example.mds.resolver;

import com.example.mds.entity.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewResolver implements Resolver {

    private final String REDIRECT_PREFIX = "redirect:";
    private final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views";
    private final String VIEW_RESOLVER_SUFFIX = ".jsp";

    @Override
    public void resolve(ServletRequest request, ServletResponse response, Object result) throws ServletException, IOException {
        ModelAndView mav = new ModelAndView();
        if (result instanceof ModelAndView) {
            mav = (ModelAndView) result;
        } else if (result instanceof String) {
            mav.setUrl((String) result);
        }

        if (mav.getUrl().startsWith(REDIRECT_PREFIX)) {
            mav.setUrl(mav.getUrl().replaceAll(REDIRECT_PREFIX, ""));
            ((HttpServletResponse) response).sendRedirect(mav.getUrl());
        } else {
            mav.getParameterMap().forEach(request::setAttribute);
            RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_RESOLVER_PREFIX + mav.getUrl() + VIEW_RESOLVER_SUFFIX);
            dispatcher.forward(request, response);
        }
    }
}
