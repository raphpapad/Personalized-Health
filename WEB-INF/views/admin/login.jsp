<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Administrator Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="auth-container">

    <div class="auth-card">

        <h2>Administrator Login</h2>

        <% if (request.getParameter("error") != null) { %>

            <div class="alert error">
                Invalid administrator credentials.
            </div>

        <% } %>


        <form method="post"
              action="${pageContext.request.contextPath}/admin/login">

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
            <a href="${pageContext.request.contextPath}/index.jsp">
                Back to Home
            </a>
        </p>

    </div>

</div>

</body>

</html>
