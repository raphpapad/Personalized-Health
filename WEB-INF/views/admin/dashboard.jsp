<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.medical.system.model.User" %>

<%
    List<User> users =
        (List<User>) request.getAttribute("users");

    List<User> pendingDoctors =
        (List<User>) request.getAttribute(
            "pendingDoctors"
        );
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Administrator Dashboard</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<nav class="navbar">

    <strong>Administrator Portal</strong>

    <a href="${pageContext.request.contextPath}/logout">
        Logout
    </a>

</nav>


<main class="container">

    <h1>Administrator Dashboard</h1>


    <section class="card">

        <h2>All Users</h2>

        <table>

            <thead>

            <tr>

                <th>Username</th>

                <th>Name</th>

                <th>Date of Birth</th>

                <th>Role</th>

                <th>Status</th>

                <th>Action</th>

            </tr>

            </thead>

            <tbody>

            <% for (User user : users) { %>

                <tr>

                    <td>
                        <%= user.getUsername() %>
                    </td>

                    <td>
                        <%= user.getFirstName() %>
                        <%= user.getLastName() %>
                    </td>

                    <td>
                        <%= user.getDateOfBirth() %>
                    </td>

                    <td>
                        <%= user.getRole() %>
                    </td>

                    <td>

                        <% if ("DOCTOR".equals(
                                user.getRole())) { %>

                            <% if (user.isCertified()) { %>

                                <span class="badge success-badge">
                                    Certified
                                </span>

                            <% } else { %>

                                <span class="badge warning-badge">
                                    Pending
                                </span>

                            <% } %>

                        <% } else { %>

                            <span class="badge">
                                Registered User
                            </span>

                        <% } %>

                    </td>

                    <td>

                        <form method="post"
                              action="${pageContext.request.contextPath}/admin/delete-user"
                              onsubmit="return confirm(
                                'Delete this user?'
                              );">

                            <input type="hidden"
                                   name="id"
                                   value="<%= user.getId() %>">

                            <button class="button danger small"
                                    type="submit">
                                Delete
                            </button>

                        </form>

                    </td>

                </tr>

            <% } %>

            </tbody>

        </table>

    </section>


    <section class="card">

        <h2>Pending Doctor Certifications</h2>

        <% if (pendingDoctors.isEmpty()) { %>

            <p>
                There are no pending doctor applications.
            </p>

        <% } else { %>

            <table>

                <thead>

                <tr>

                    <th>Username</th>

                    <th>Name</th>

                    <th>AMKA</th>

                    <th>Specialty</th>

                    <th>Email</th>

                    <th>Phone</th>

                    <th>Action</th>

                </tr>

                </thead>

                <tbody>

                <% for (User doctor :
                        pendingDoctors) { %>

                    <tr>

                        <td>
                            <%= doctor.getUsername() %>
                        </td>

                        <td>
                            <%= doctor.getFirstName() %>
                            <%= doctor.getLastName() %>
                        </td>

                        <td>
                            <%= doctor.getAmka() %>
                        </td>

                        <td>
                            <%= doctor.getSpecialty() %>
                        </td>

                        <td>
                            <%= doctor.getEmail() %>
                        </td>

                        <td>
                            <%= doctor.getPhone() %>
                        </td>

                        <td>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/certify-doctor">

                                <input type="hidden"
                                       name="id"
                                       value="<%= doctor.getId() %>">

                                <button class="button success-button"
                                        type="submit">
                                    Certify Doctor
                                </button>

                            </form>

                        </td>

                    </tr>

                <% } %>

                </tbody>

            </table>

        <% } %>

    </section>

</main>


<footer>

    <p>
        Medical Management System
    </p>

</footer>

</body>

</html>
