package com.abhijit.accountsvc.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.io.IOException;



@Component
@Slf4j
public class AppFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("remote address:"+request.getRemoteAddr());
        chain.doFilter(request,response);
        log.info("response content:"+response.getContentType());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
