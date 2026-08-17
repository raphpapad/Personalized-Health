<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.medical.system.model.Patient" %>

<%
    List<Patient> patients =
        (List<Patient>) request.getAttribute("patients");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Doctor Dashboard</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<nav class="navbar">

    <strong>Doctor Portal</strong>

    <div>

        <a href="${pageContext.request.contextPath}/doctor/profile.jsp">
            My Profile
        </a>

        <a href="${pageContext.request.contextPath}/logout">
            Logout
        </a>

    </div>

</nav>


<main class="container">

    <h1>Doctor Dashboard</h1>


    <section class="card">

        <h2>My Patients</h2>

        <table>

            <thead>

            <tr>

                <th>Username</th>

                <th>Name</th>

                <th>Date of Birth</th>

                <th>AMKA</th>

                <th>Action</th>

            </tr>

            </thead>

            <tbody>

            <% for (Patient patient : patients) { %>

                <tr>

                    <td>
                        <%= patient.getUsername() %>
                    </td>

                    <td>
                        <%= patient.getFirstName() %>
                        <%= patient.getLastName() %>
                    </td>

                    <td>
                        <%= patient.getDateOfBirth() %>
                    </td>

                    <td>
                        <%= patient.getAmka() %>
                    </td>

                    <td>

                        <button
                            class="button small"
                            onclick="viewPatient(
                                <%= patient.getId() %>
                            )">
                            View History
                        </button>

                    </td>

                </tr>

            <% } %>

            </tbody>

        </table>

    </section>


    <section class="card">

        <h2>Selected Patient</h2>

        <div id="patientHistory">

            Select a patient above.

        </div>

    </section>


    <section class="card">

        <h2>Add Treatment</h2>

        <form id="treatmentForm">

            <input type="hidden"
                   id="patientId">


            <label>Treatment Name</label>

            <input type="text"
                   id="treatmentName"
                   required>


            <label>Description</label>

            <textarea id="treatmentDescription"></textarea>


            <label>Start Date</label>

            <input type="date"
                   id="startDate"
                   required>


            <label>End Date</label>

            <input type="date"
                   id="endDate"
                   required>


            <button class="button"
                    type="submit">
                Add Treatment
            </button>

        </form>

        <div id="treatmentMessage"></div>

    </section>

</main>


<script>

    const contextPath =
        "${pageContext.request.contextPath}";


    async function viewPatient(patientId) {

        document.getElementById(
            "patientId"
        ).value = patientId;


        const examinationResponse =
            await fetch(
                contextPath +
                "/api/examinations/" +
                patientId
            );


        const examinations =
            await examinationResponse.json();


        const treatmentResponse =
            await fetch(
                contextPath +
                "/api/treatments/" +
                patientId
            );


        const treatments =
            await treatmentResponse.json();


        let html = `
            <h3>Examinations</h3>

            <table>

                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Test</th>
                        <th>Value</th>
                        <th>Unit</th>
                        <th>Reference</th>
                    </tr>
                </thead>

                <tbody>
        `;


        examinations.forEach(exam => {

            html += `
                <tr>
                    <td>${exam.examinationDate}</td>
                    <td>${exam.testName}</td>
                    <td>${exam.value}</td>
                    <td>${exam.unit || ""}</td>
                    <td>${exam.referenceRange || ""}</td>
                </tr>
            `;

        });


        html += `
                </tbody>
            </table>


            <h3>Active / Existing Treatments</h3>

            <table>

                <thead>

                    <tr>
                        <th>Treatment</th>
                        <th>Description</th>
                        <th>Start</th>
                        <th>End</th>
                    </tr>

                </thead>

                <tbody>
        `;


        treatments.forEach(treatment => {

            html += `
                <tr>

                    <td>
                        ${treatment.treatmentName}
                    </td>

                    <td>
                        ${treatment.description || ""}
                    </td>

                    <td>
                        ${treatment.startDate}
                    </td>

                    <td>
                        ${treatment.endDate}
                    </td>

                </tr>
            `;

        });


        html += `
                </tbody>
            </table>
        `;


        document.getElementById(
            "patientHistory"
        ).innerHTML = html;
    }


    document.getElementById(
        "treatmentForm"
    ).addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const data = {

                patientId:
                    parseInt(
                        document.getElementById(
                            "patientId"
                        ).value
                    ),

                treatmentName:
                    document.getElementById(
                        "treatmentName"
                    ).value,

                description:
                    document.getElementById(
                        "treatmentDescription"
                    ).value,

                startDate:
                    document.getElementById(
                        "startDate"
                    ).value,

                endDate:
                    document.getElementById(
                        "endDate"
                    ).value
            };


            const response =
                await fetch(
                    contextPath +
                    "/api/treatments",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(data)
                    }
                );


            const result =
                await response.json();


            if (result.success) {

                document.getElementById(
                    "treatmentMessage"
                ).innerHTML =
                    '<div class="alert success">' +
                    'Treatment added successfully.' +
                    '</div>';

            } else {

                document.getElementById(
                    "treatmentMessage"
                ).innerHTML =
                    '<div class="alert error">' +
                    'Could not add treatment.' +
                    '</div>';
            }

        }
    );

</script>

</body>

</html>
