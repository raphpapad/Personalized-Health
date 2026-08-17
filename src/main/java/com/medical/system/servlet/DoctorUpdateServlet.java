package com.medical.system.servlet;

import com.medical.system.dao.UserDAO;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/doctor/update")
public class DoctorUpdateServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        HttpSession session =
                request.getSession(false);

        User currentUser =
                (User) session.getAttribute("user");

        User doctor = new User();

        doctor.setId(currentUser.getId());

        doctor.setFirstName(
                request.getParameter("firstName")
        );

        doctor.setLastName(
                request.getParameter("lastName")
        );

        doctor.setDateOfBirth(
                Date.valueOf(
                        request.getParameter("dateOfBirth")
                )
        );

        doctor.setEmail(
                request.getParameter("email")
        );

        doctor.setSpecialty(
                request.getParameter("specialty")
        );

        doctor.setPhone(
                request.getParameter("phone")
        );

        userDAO.updateDoctor(doctor);

        User updated =
                userDAO.getUserById(
                        currentUser.getId()
                );

        session.setAttribute("user", updated);

        response.sendRedirect(
                request.getContextPath()
                + "/doctor/profile.jsp?success=1"
        );
    }
}
