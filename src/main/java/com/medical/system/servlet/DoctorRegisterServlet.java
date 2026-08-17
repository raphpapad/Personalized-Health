package com.medical.system.servlet;

import com.medical.system.dao.UserDAO;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/doctor/register")
public class DoctorRegisterServlet extends HttpServlet {

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

        String firstName =
                request.getParameter("firstName");

        String lastName =
                request.getParameter("lastName");

        String dob =
                request.getParameter("dateOfBirth");

        String amka =
                request.getParameter("amka");

        String email =
                request.getParameter("email");

        String specialty =
                request.getParameter("specialty");

        String phone =
                request.getParameter("phone");

        if (userDAO.usernameExists(username)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/doctor/register.jsp?error=username"
            );

            return;
        }

        if (userDAO.amkaExists(amka)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/doctor/register.jsp?error=amka"
            );

            return;
        }

        User doctor = new User();

        doctor.setUsername(username);
        doctor.setPassword(password);
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setDateOfBirth(
                Date.valueOf(dob)
        );
        doctor.setAmka(amka);
        doctor.setEmail(email);
        doctor.setSpecialty(specialty);
        doctor.setPhone(phone);

        if (userDAO.registerDoctor(doctor)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/doctor/login.jsp?registered=1"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/doctor/register.jsp?error=1"
            );
        }
    }
}
