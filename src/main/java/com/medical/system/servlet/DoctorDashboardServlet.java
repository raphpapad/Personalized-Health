package com.medical.system.servlet;

import com.medical.system.dao.DoctorDAO;
import com.medical.system.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/doctor/dashboard")
public class DoctorDashboardServlet extends HttpServlet {

    private final DoctorDAO doctorDAO = new DoctorDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        User doctor =
                (User) session.getAttribute("user");

        request.setAttribute(
                "patients",
                doctorDAO.getPatientsForDoctor(
                        doctor.getId()
                )
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/doctor/dashboard.jsp"
        ).forward(request, response);
    }
}
