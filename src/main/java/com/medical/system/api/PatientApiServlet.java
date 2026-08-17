package com.medical.system.api;

import com.google.gson.Gson;
import com.medical.system.dao.DoctorDAO;
import com.medical.system.model.Patient;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/patients/*")
public class PatientApiServlet extends HttpServlet {

    private final DoctorDAO doctorDAO =
            new DoctorDAO();

    private final Gson gson =
            new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            response.setStatus(401);
            return;
        }

        User doctor =
                (User) session.getAttribute("user");

        if (!"DOCTOR".equals(doctor.getRole())) {

            response.setStatus(403);
            return;
        }

        List<Patient> patients =
                doctorDAO.getPatientsForDoctor(
                        doctor.getId()
                );

        response.setContentType(
                "application/json"
        );

        response.getWriter().write(
                gson.toJson(patients)
        );
    }
}
