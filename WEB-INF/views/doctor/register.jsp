<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Doctor Registration</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="auth-container">

    <div class="auth-card">

        <h2>Doctor Registration</h2>

        <p class="warning-text">
            Doctor accounts must be certified by an administrator
            before they can log in.
        </p>

        <% if ("username".equals(
                request.getParameter("error"))) { %>

            <div class="alert error">
                Username already exists.
            </div>

        <% } %>


        <% if ("amka".equals(
                request.getParameter("error"))) { %>

            <div class="alert error">
                AMKA already exists.
            </div>

        <% } %>


        <form method="post"
              action="${pageContext.request.contextPath}/doctor/register">

            <label>Username</label>

            <input type="text"
                   name="username"
                   required>


            <label>Password</label>

            <input type="password"
                   name="password"
                   required>


            <label>First Name</label>

            <input type="text"
                   name="firstName"
                   required>


            <label>Last Name</label>

            <input type="text"
                   name="lastName"
                   required>


            <label>Date of Birth</label>

            <input type="date"
                   name="dateOfBirth"
                   required>


            <label>AMKA</label>

            <input type="text"
                   name="amka"
                   required>


            <label>Email</label>

            <input type="email"
                   name="email"
                   required>


            <label>Specialty</label>

            <input type="text"
                   name="specialty"
                   placeholder="Cardiologist"
                   required>


            <label>Phone</label>

            <input type="text"
                   name="phone">


            <button class="button"
                    type="submit">
                Register
            </button>

        </form>

    </div>

</div>

</body>

</html>
