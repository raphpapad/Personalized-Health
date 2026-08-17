package com.medical.system.servlet;

import com.medical.system.dao.UserDAO;
import com.medical.system.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {

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

        User user = new User();

        user.setId(currentUser.getId());

        user.setFirstName(
                request.getParameter("firstName")
        );

        user.setLastName(
                request.getParameter("lastName")
        );

        user.setDateOfBirth(
                Date.valueOf(
                        request.getParameter("dateOfBirth")
                )
        );

        user.setEmail(
                request.getParameter("email")
        );

        userDAO.updateUser(user);

        User updated =
                userDAO.getUserById(
                        currentUser.getId()
                );

        session.setAttribute("user", updated);

        response.sendRedirect(
                request.getContextPath()
                + "/user/profile.jsp?success=1"
        );
    }
}
