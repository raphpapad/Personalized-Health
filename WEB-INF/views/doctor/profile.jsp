<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.medical.system.model.User" %>

<%
    User doctor =
        (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Doctor Profile</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="container">

    <h1>Doctor Profile</h1>

    <% if (request.getParameter("success") != null) { %>

        <div class="alert success">
            Profile updated successfully.
        </div>

    <% } %>


    <form method="post"
          action="${pageContext.request.contextPath}/doctor/update">

        <label>Username</label>

        <input type="text"
               value="<%= doctor.getUsername() %>"
               disabled>


        <label>AMKA</label>

        <input type="text"
               value="<%= doctor.getAmka() %>"
               disabled>


        <label>First Name</label>

        <input type="text"
               name="firstName"
               value="<%= doctor.getFirstName() %>"
               required>


        <label>Last Name</label>

        <input type="text"
               name="lastName"
               value="<%= doctor.getLastName() %>"
               required>


        <label>Date of Birth</label>

        <input type="date"
               name="dateOfBirth"
               value="<%= doctor.getDateOfBirth() %>"
               required>


        <label>Email</label>

        <input type="email"
               name="email"
               value="<%= doctor.getEmail() %>"
               required>


        <label>Specialty</label>

        <input type="text"
               name="specialty"
               value="<%= doctor.getSpecialty() %>"
               required>


        <label>Phone</label>

        <input type="text"
               name="phone"
               value="<%= doctor.getPhone() %>">


        <button class="button"
                type="submit">
            Save Changes
        </button>

    </form>


    <p>

        <a class="button"
           href="${pageContext.request.contextPath}/doctor/dashboard">
            Back to Dashboard
        </a>

    </p>

</div>

</body>

</html>
