package com.medical.system.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session =
                httpRequest.getSession(false);

        String uri = httpRequest.getRequestURI();

        boolean loggedIn =
                session != null &&
                session.getAttribute("user") != null;

        boolean publicPage =
                uri.endsWith("index.jsp")
                || uri.endsWith("login")
                || uri.endsWith("register")
                || uri.contains("/css/")
                || uri.contains("/js/")
                || uri.contains("/api/doctors");

        if (loggedIn || publicPage) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                    + "/index.jsp"
            );
        }
    }
}
