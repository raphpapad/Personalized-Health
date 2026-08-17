package com.medical.system.api;

import com.google.gson.Gson;
import com.medical.system.dao.DoctorDAO;
import com.medical.system.dao.UserDAO;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/doctors/*")
public class DoctorApiServlet extends HttpServlet {

    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final UserDAO userDAO = new UserDAO();

    private final Gson gson = new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                gson.toJson(
                        doctorDAO.getCertifiedDoctors()
                )
        );
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            return;
        }

        User patient =
                (User) session.getAttribute("user");

        if (!"USER".equals(patient.getRole())) {

            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN
            );

            return;
        }

        String doctorIdString =
                request.getParameter("doctorId");

        int doctorId =
                Integer.parseInt(doctorIdString);

        boolean result =
                doctorDAO.assignPatientToDoctor(
                        doctorId,
                        patient.getId()
                );

        response.setContentType(
                "application/json"
        );

        response.getWriter().write(
                gson.toJson(
                        Map.of(
                                "success",
                                result
                        )
                )
        );
    }
}
