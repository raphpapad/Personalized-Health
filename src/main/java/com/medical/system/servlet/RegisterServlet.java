package com.medical.system.servlet;

import com.medical.system.dao.UserDAO;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

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

        if (userDAO.usernameExists(username)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/user/register.jsp?error=username"
            );

            return;
        }

        if (userDAO.amkaExists(amka)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/user/register.jsp?error=amka"
            );

            return;
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(
                Date.valueOf(dob)
        );
        user.setAmka(amka);
        user.setEmail(email);

        if (userDAO.registerUser(user)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/user/login.jsp?registered=1"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/user/register.jsp?error=1"
            );
        }
    }
}
