<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Doctor Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="auth-container">

    <div class="auth-card">

        <h2>Doctor Login</h2>

        <% if (request.getParameter("error") != null) { %>

            <div class="alert error">
                Invalid username or password.
            </div>

        <% } %>

        <% if (request.getParameter("pending") != null) { %>

            <div class="alert warning">
                Your doctor account has not yet been certified
                by an administrator.
            </div>

        <% } %>

        <% if (request.getParameter("registered") != null) { %>

            <div class="alert success">
                Registration completed.
                Please wait for administrator certification.
            </div>

        <% } %>


        <form method="post"
              action="${pageContext.request.contextPath}/doctor/login">

            <label>Username</label>

            <input type="text"
                   name="username"
                   required>


            <label>Password</label>

            <input type="password"
                   name="password"
                   required>


            <button class="button"
                    type="submit">
                Login
            </button>

        </form>


        <p>
            New doctor?
            <a href="${pageContext.request.contextPath}/doctor/register.jsp">
                Register
            </a>
        </p>

        <p>
            <a href="${pageContext.request.contextPath}/index.jsp">
                Back to Home
            </a>
        </p>

    </div>

</div>

</body>

</html>
