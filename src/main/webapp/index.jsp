<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Medical Management System</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<header class="main-header">

    <div class="container">

        <h1>Medical Management System</h1>

        <p>
            A simple web-based platform for patients,
            doctors and administrators.
        </p>

    </div>

</header>


<main class="container">

    <section class="hero">

        <h2>Welcome</h2>

        <p>
            Select the type of account you want to use.
        </p>

    </section>


    <div class="card-grid">

        <div class="card">

            <h3>Registered User</h3>

            <p>
                Search doctors, select your doctor,
                manage examinations and view treatments.
            </p>

            <a class="button"
               href="${pageContext.request.contextPath}/user/login.jsp">
                User Login
            </a>

            <a class="button secondary"
               href="${pageContext.request.contextPath}/user/register.jsp">
                Register
            </a>

        </div>


        <div class="card">

            <h3>Doctor</h3>

            <p>
                Manage patients, view medical examinations
                and create treatments.
            </p>

            <a class="button"
               href="${pageContext.request.contextPath}/doctor/login.jsp">
                Doctor Login
            </a>

            <a class="button secondary"
               href="${pageContext.request.contextPath}/doctor/register.jsp">
                Doctor Registration
            </a>

        </div>


        <div class="card">

            <h3>Administrator</h3>

            <p>
                Manage users and certify pending doctors.
            </p>

            <a class="button"
               href="${pageContext.request.contextPath}/admin/login.jsp">
                Administrator Login
            </a>

        </div>

    </div>


    <section class="information">

        <h2>Useful Information</h2>

        <p>
            The links below provide external information
            about healthcare services.
        </p>

        <div class="external-links">

            <a href="https://www.google.com/maps/search/pharmacies/"
               target="_blank">
                Find Pharmacies
            </a>

            <a href="https://www.google.com/maps/search/hospitals/"
               target="_blank">
                Find Hospitals
            </a>

            <a href="https://www.google.com/search?q=emergency+medical+services"
               target="_blank">
                Emergency Medical Information
            </a>

            <a href="https://www.who.int/"
               target="_blank">
                World Health Organization
            </a>

        </div>

    </section>

</main>


<footer>

    <p>
        Medical Management System &copy; 2026
    </p>

    <p>
        External healthcare information:
        Pharmacies | Hospitals | Emergency Services | WHO
    </p>

</footer>

</body>

</html>
