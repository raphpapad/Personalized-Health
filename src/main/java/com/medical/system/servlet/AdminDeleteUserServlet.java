package com.medical.system.servlet;

import com.medical.system.dao.UserDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/delete-user")
public class AdminDeleteUserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        userDAO.deleteUser(id);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/dashboard"
        );
    }
}
