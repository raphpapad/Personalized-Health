package com.medical.system.api;

import com.google.gson.Gson;
import com.medical.system.dao.DoctorDAO;
import com.medical.system.dao.TreatmentDAO;
import com.medical.system.model.Treatment;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

@WebServlet("/api/treatments/*")
public class TreatmentApiServlet extends HttpServlet {

    private final TreatmentDAO treatmentDAO =
            new TreatmentDAO();

    private final DoctorDAO doctorDAO =
            new DoctorDAO();

    private final Gson gson =
            new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String path =
                request.getPathInfo();

        if (path == null || path.equals("/")) {

            response.setStatus(400);
            return;
        }

        int patientId =
                Integer.parseInt(
                        path.substring(1)
                );

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            response.setStatus(401);
            return;
        }

        User user =
                (User) session.getAttribute("user");

        boolean allowed =
                "USER".equals(user.getRole())
                && user.getId() == patientId;

        if ("DOCTOR".equals(user.getRole())) {

            allowed =
                    doctorDAO.isDoctorAssignedToPatient(
                            user.getId(),
                            patientId
                    );
        }

        if (!allowed) {

            response.setStatus(403);
            return;
        }

        response.setContentType(
                "application/json"
        );

        response.getWriter().write(
                gson.toJson(
                        treatmentDAO.getAllTreatments(
                                patientId
                        )
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

            response.setStatus(401);
            return;
        }

        User doctor =
                (User) session.getAttribute("user");

        if (!"DOCTOR".equals(doctor.getRole())) {

            response.setStatus(403);
            return;
        }

        TreatmentRequest body =
                gson.fromJson(
                        request.getReader(),
                        TreatmentRequest.class
                );

        int patientId =
                body.patientId;

        if (!doctorDAO.isDoctorAssignedToPatient(
                doctor.getId(),
                patientId
        )) {

            response.setStatus(403);
            return;
        }

        Treatment treatment =
                new Treatment();

        treatment.setDoctorId(
                doctor.getId()
        );

        treatment.setPatientId(
                patientId
        );

        treatment.setTreatmentName(
                body.treatmentName
        );

        treatment.setDescription(
                body.description
        );

        treatment.setStartDate(
                Date.valueOf(
                        body.startDate
                )
        );

        treatment.setEndDate(
                Date.valueOf(
                        body.endDate
                )
        );

        boolean success =
                treatmentDAO.addTreatment(
                        treatment
                );

        response.setContentType(
                "application/json"
        );

        response.getWriter().write(
                gson.toJson(
                        Map.of(
                                "success",
                                success
                        )
                )
        );
    }

    private static class TreatmentRequest {

        int patientId;

        String treatmentName;

        String description;

        String startDate;

        String endDate;
    }
}
