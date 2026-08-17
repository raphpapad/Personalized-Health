package com.medical.system.servlet;

import com.medical.system.dao.UserDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/certify-doctor")
public class AdminCertificationServlet
        extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        userDAO.certifyDoctor(id);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/dashboard"
        );
    }
}
