package com.demo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AuthFilter implements Filter {

    // Các action được phép truy cập mà không cần login
    private Set<String> publicActions;

    // Các method trong DispatchAction được phép truy cập mà không cần login
    private Set<String> publicMethods;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthenticationFilter initialized");

        // Khởi tạo danh sách các action public
        publicActions = new HashSet<String>();
        publicActions.add("/login.do");
        publicActions.add("/logout.do");

        // Khởi tạo danh sách các method public
        publicMethods = new HashSet<String>();
        publicMethods.add("login");
        publicMethods.add("logout");

        // Đọc từ init-param nếu có
        String publicActionsParam = filterConfig.getInitParameter("publicActions");
        if (publicActionsParam != null && !publicActionsParam.trim().isEmpty()) {
            String[] actions = publicActionsParam.split(",");
            publicActions.addAll(Arrays.asList(actions));
        }

        String publicMethodsParam = filterConfig.getInitParameter("publicMethods");
        if (publicMethodsParam != null && !publicMethodsParam.trim().isEmpty()) {
            String[] methods = publicMethodsParam.split(",");
            publicMethods.addAll(Arrays.asList(methods));
        }

        System.out.println("Public Actions: " + publicActions);
        System.out.println("Public Methods: " + publicMethods);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Lấy đường dẫn sau context path
        String path = requestURI.substring(contextPath.length());

        System.out.println("=== AuthenticationFilter ===");
        System.out.println("Request URI: " + requestURI);
        System.out.println("Context Path: " + contextPath);
        System.out.println("Path: " + path);

        // Kiểm tra xem user đã login chưa
        boolean isLoggedIn = (session != null && session.getAttribute("currentUser") != null);
        System.out.println("Is Logged In: " + isLoggedIn);

        // Kiểm tra xem có phải là public resource không
        boolean isPublicResource = isPublicResource(path);

        // Kiểm tra xem có phải là public action không
        boolean isPublicAction = isPublicAction(path);

        // Kiểm tra xem có phải là public method trong DispatchAction không
        boolean isPublicMethod = false;
        if (!isPublicAction) {
            String method = httpRequest.getParameter("method");
            isPublicMethod = isPublicMethod(method);
            System.out.println("Method parameter: " + method);
            System.out.println("Is Public Method: " + isPublicMethod);
        }

        if (isLoggedIn || isPublicResource || isPublicAction || isPublicMethod) {
            // Cho phép truy cập
            System.out.println("Access granted");
            chain.doFilter(request, response);
        } else {
            // Chưa login, redirect về trang login
            System.out.println("Access denied - redirecting to login page");

            // Lưu URL hiện tại để redirect lại sau khi login
            String requestedURL = httpRequest.getRequestURI();
            String queryString = httpRequest.getQueryString();
            if (queryString != null && !queryString.isEmpty()) {
                requestedURL += "?" + queryString;
            }

            // Tạo session mới nếu chưa có
            if (session == null) {
                session = httpRequest.getSession(true);
            }
            session.setAttribute("requestedURL", requestedURL);

            System.out.println("Saved requested URL: " + requestedURL);

            // Redirect về login page
            httpResponse.sendRedirect(contextPath + "/login.jsp");
        }
    }

    /**
     * Kiểm tra xem có phải public resource không (css, js, images...)
     */
    private boolean isPublicResource(String path) {
        return path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".gif") ||
                path.endsWith(".ico") ||
                path.equals("/login.jsp");
    }

    /**
     * Kiểm tra xem có phải public action không
     */
    private boolean isPublicAction(String path) {
        for (String publicAction : publicActions) {
            if (path.equals(publicAction)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kiểm tra xem có phải public method không
     */
    private boolean isPublicMethod(String method) {
        if (method == null || method.trim().isEmpty()) {
            return false;
        }
        return publicMethods.contains(method.trim());
    }

    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter destroyed");
    }
}