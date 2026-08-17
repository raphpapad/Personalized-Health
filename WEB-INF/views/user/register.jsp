<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>User Registration</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="auth-container">

    <div class="auth-card">

        <h2>Registered User Registration</h2>

        <% if ("username".equals(request.getParameter("error"))) { %>

            <div class="alert error">
                Username already exists.
            </div>

        <% } %>

        <% if ("amka".equals(request.getParameter("error"))) { %>

            <div class="alert error">
                AMKA already exists.
            </div>

        <% } %>


        <form method="post"
              action="${pageContext.request.contextPath}/register">

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
                   maxlength="20"
                   required>


            <label>Email</label>

            <input type="email"
                   name="email"
                   required>


            <button type="submit"
                    class="button">
                Register
            </button>

        </form>


        <p>
            Already registered?
            <a href="${pageContext.request.contextPath}/user/login.jsp">
                Login
            </a>
        </p>

    </div>

</div>

</body>

</html>
