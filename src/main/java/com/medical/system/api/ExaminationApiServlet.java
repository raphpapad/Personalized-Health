package com.medical.system.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medical.system.dao.DoctorDAO;
import com.medical.system.dao.ExaminationDAO;
import com.medical.system.model.Examination;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/api/examinations/*")
public class ExaminationApiServlet extends HttpServlet {

    private final ExaminationDAO examinationDAO =
            new ExaminationDAO();

    private final DoctorDAO doctorDAO =
            new DoctorDAO();

    private final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String path =
                request.getPathInfo();

        if (path == null || path.equals("/")) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

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

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            return;
        }

        User currentUser =
                (User) session.getAttribute("user");

        boolean allowed =
                "USER".equals(currentUser.getRole())
                && currentUser.getId() == patientId;

        if ("DOCTOR".equals(currentUser.getRole())) {

            allowed =
                    doctorDAO.isDoctorAssignedToPatient(
                            currentUser.getId(),
                            patientId
                    );
        }

        if (!allowed) {

            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN
            );

            return;
        }

        List<Examination> examinations =
                examinationDAO.getPatientExaminations(
                        patientId
                );

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                gson.toJson(examinations)
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

        User user =
                (User) session.getAttribute("user");

        if (!"USER".equals(user.getRole())) {

            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN
            );

            return;
        }

        BufferedReader reader =
                request.getReader();

        ExaminationRequest body =
                gson.fromJson(
                        reader,
                        ExaminationRequest.class
                );

        Examination examination =
                new Examination();

        examination.setPatientId(
                user.getId()
        );

        examination.setExaminationDate(
                Date.valueOf(
                        body.examinationDate
                )
        );

        examination.setTestName(
                body.testName
        );

        examination.setValue(
                body.value
        );

        examination.setUnit(
                body.unit
        );

        examination.setReferenceRange(
                body.referenceRange
        );

        examination.setNotes(
                body.notes
        );

        boolean success =
                examinationDAO.addExamination(
                        examination
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

    private static class ExaminationRequest {

        String examinationDate;
        String testName;
        double value;
        String unit;
        String referenceRange;
        String notes;
    }
}
