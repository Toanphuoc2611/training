package com.demo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Loại bỏ context path để lấy path tương đối
        String path = requestURI.substring(contextPath.length());

        // Cho phép truy cập login.jsp và /login.do
        if (path.equals("/login.jsp") || path.equals("/login.do")) {
            chain.doFilter(request, response);
            return;
        }

        // Kiểm tra session cho các trang khác
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Chưa đăng nhập, redirect về login
            httpResponse.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // Đã đăng nhập, cho phép tiếp tục
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}