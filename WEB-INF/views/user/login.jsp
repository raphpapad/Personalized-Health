<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>User Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="auth-container">

    <div class="auth-card">

        <h2>Registered User Login</h2>

        <% if (request.getParameter("error") != null) { %>

            <div class="alert error">
                Invalid username or password.
            </div>

        <% } %>

        <% if (request.getParameter("registered") != null) { %>

            <div class="alert success">
                Registration completed successfully.
                You can now log in.
            </div>

        <% } %>


        <form method="post"
              action="${pageContext.request.contextPath}/login">

            <label>Username</label>

            <input type="text"
                   name="username"
                   required>


            <label>Password</label>

            <input type="password"
                   name="password"
                   required>


            <button type="submit"
                    class="button">
                Login
            </button>

        </form>


        <p>
            Don't have an account?
            <a href="${pageContext.request.contextPath}/user/register.jsp">
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
