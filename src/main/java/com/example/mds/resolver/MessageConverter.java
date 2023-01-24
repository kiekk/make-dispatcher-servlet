package com.example.mds.resolver;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageConverter implements Resolver {
    @Override
    public void resolve(ServletRequest request, ServletResponse response, Object result) throws IOException {
        System.out.println("MessageConverter로 처리된 결과 : " + result);

        PrintWriter writer = response.getWriter();
        writer.println(result);
        writer.close();
    }
}
