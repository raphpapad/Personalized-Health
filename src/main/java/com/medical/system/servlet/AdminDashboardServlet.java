package com.medical.system.servlet;

import com.medical.system.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setAttribute(
                "users",
                userDAO.getAllUsers()
        );

        request.setAttribute(
                "pendingDoctors",
                userDAO.getPendingDoctors()
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/dashboard.jsp"
        ).forward(request, response);
    }
}
