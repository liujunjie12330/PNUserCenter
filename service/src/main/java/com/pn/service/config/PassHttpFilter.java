package com.pn.service.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 项目跨域配置
 * @author liujunjie
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "authFilter") //这里的“/*” 表示的是需要拦截的请求路径
public class PassHttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest  httpServletRequest = (HttpServletRequest) servletRequest;
        //解决跨域的问题
        response.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-App-Id, X-Token");
        response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy() {
    }
}

