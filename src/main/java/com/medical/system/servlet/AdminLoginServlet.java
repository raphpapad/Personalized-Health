package com.medical.system.servlet;

import com.medical.system.dao.UserDAO;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        User user =
                userDAO.authenticate(
                        username,
                        password,
                        "ADMIN"
                );

        if (user == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/login.jsp?error=1"
            );

            return;
        }

        HttpSession session =
                request.getSession();

        session.setAttribute("user", user);
        session.setAttribute("role", "ADMIN");

        response.sendRedirect(
                request.getContextPath()
                + "/admin/dashboard"
        );
    }
}
