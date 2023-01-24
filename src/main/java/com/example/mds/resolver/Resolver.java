package com.example.mds.resolver;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public interface Resolver {
    void resolve(ServletRequest request, ServletResponse response, Object result) throws IOException, ServletException;
}
