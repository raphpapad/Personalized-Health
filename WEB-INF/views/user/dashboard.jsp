<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>User Dashboard</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<nav class="navbar">

    <div>

        <strong>Medical System</strong>

    </div>

    <div>

        <a href="${pageContext.request.contextPath}/user/profile.jsp">
            My Profile
        </a>

        <a href="${pageContext.request.contextPath}/logout">
            Logout
        </a>

    </div>

</nav>


<main class="container">

    <h1>User Dashboard</h1>


    <section class="card">

        <h2>Find a Doctor</h2>

        <p>
            Select a doctor to establish a patient-doctor relationship.
        </p>

        <div id="doctorsContainer">

            Loading doctors...

        </div>

    </section>


    <section class="card">

        <h2>Add Examination</h2>

        <form id="examinationForm">

            <label>Examination Date</label>

            <input type="date"
                   id="examinationDate"
                   required>


            <label>Test Name</label>

            <input type="text"
                   id="testName"
                   placeholder="Glucose"
                   required>


            <label>Value</label>

            <input type="number"
                   step="0.01"
                   id="testValue"
                   required>


            <label>Unit</label>

            <input type="text"
                   id="unit"
                   placeholder="mg/dL">


            <label>Reference Range</label>

            <input type="text"
                   id="referenceRange"
                   placeholder="70-100">


            <label>Notes</label>

            <textarea id="notes"></textarea>


            <button class="button"
                    type="submit">
                Add Examination
            </button>

        </form>

        <div id="examinationMessage"></div>

    </section>


    <section class="card">

        <h2>My Examinations</h2>

        <button class="button"
                onclick="loadMyExaminations()">
            Refresh Examinations
        </button>

        <div id="myExaminations">

            Click Refresh Examinations.

        </div>

    </section>


    <section class="card">

        <h2>My Treatments</h2>

        <button class="button"
                onclick="loadMyTreatments()">
            Refresh Treatments
        </button>

        <div id="myTreatments">

            Click Refresh Treatments.

        </div>

    </section>

</main>


<footer>

    <p>
        Pharmacies |
        Hospitals |
        Emergency Services |
        <a href="https://www.who.int/"
           target="_blank">
            WHO
        </a>
    </p>

</footer>


<script>

    const contextPath =
        "${pageContext.request.contextPath}";

    let currentUserId = null;


    async function loadDoctors() {

        const response =
            await fetch(
                contextPath + "/api/doctors"
            );

        const doctors =
            await response.json();

        let html = `
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Specialty</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
        `;

        doctors.forEach(doctor => {

            html += `
                <tr>
                    <td>
                        Dr. ${doctor.firstName}
                        ${doctor.lastName}
                    </td>

                    <td>
                        ${doctor.specialty || ""}
                    </td>

                    <td>
                        ${doctor.email || ""}
                    </td>

                    <td>
                        <button
                            class="button small"
                            onclick="selectDoctor(${doctor.id})">
                            Select Doctor
                        </button>
                    </td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>
        `;

        document.getElementById(
            "doctorsContainer"
        ).innerHTML = html;
    }


    async function selectDoctor(doctorId) {

        const response =
            await fetch(
                contextPath + "/api/doctors",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/x-www-form-urlencoded"
                    },

                    body:
                        "doctorId=" + doctorId
                }
            );

        const result =
            await response.json();

        if (result.success) {

            alert(
                "Doctor selected successfully."
            );

        } else {

            alert(
                "The doctor was already selected."
            );
        }
    }


    document.getElementById(
        "examinationForm"
    ).addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const examination = {

                examinationDate:
                    document.getElementById(
                        "examinationDate"
                    ).value,

                testName:
                    document.getElementById(
                        "testName"
                    ).value,

                value:
                    parseFloat(
                        document.getElementById(
                            "testValue"
                        ).value
                    ),

                unit:
                    document.getElementById(
                        "unit"
                    ).value,

                referenceRange:
                    document.getElementById(
                        "referenceRange"
                    ).value,

                notes:
                    document.getElementById(
                        "notes"
                    ).value
            };


            const response =
                await fetch(
                    contextPath +
                    "/api/examinations",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(
                                examination
                            )
                    }
                );


            const result =
                await response.json();


            if (result.success) {

                document.getElementById(
                    "examinationMessage"
                ).innerHTML =
                    '<div class="alert success">' +
                    'Examination added successfully.' +
                    '</div>';

                document.getElementById(
                    "examinationForm"
                ).reset();

                loadMyExaminations();

            } else {

                document.getElementById(
                    "examinationMessage"
                ).innerHTML =
                    '<div class="alert error">' +
                    'Could not add examination.' +
                    '</div>';
            }
        }
    );


    async function loadMyExaminations() {

        /*
         * The current user ID is retrieved by calling
         * the session endpoint below.
         */

        const response =
            await fetch(
                contextPath +
                "/api/examinations/current"
            );

        if (!response.ok) {

            document.getElementById(
                "myExaminations"
            ).innerHTML =
                "Unable to load examinations.";

            return;
        }

        const examinations =
            await response.json();

        renderExaminations(
            examinations
        );
    }


    function renderExaminations(
        examinations
    ) {

        if (examinations.length === 0) {

            document.getElementById(
                "myExaminations"
            ).innerHTML =
                "No examinations found.";

            return;
        }


        let html = `
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Test</th>
                        <th>Value</th>
                        <th>Unit</th>
                        <th>Reference</th>
                        <th>Difference</th>
                    </tr>
                </thead>
                <tbody>
        `;


        const previousValues = {};


        examinations.forEach(exam => {

            let difference = "-";


            if (
                previousValues[exam.testName]
                !== undefined
            ) {

                difference =
                    (
                        exam.value -
                        previousValues[
                            exam.testName
                        ]
                    ).toFixed(2);
            }


            previousValues[
                exam.testName
            ] = exam.value;


            html += `
                <tr>
                    <td>${exam.examinationDate}</td>
                    <td>${exam.testName}</td>
                    <td>${exam.value}</td>
                    <td>${exam.unit || ""}</td>
                    <td>${exam.referenceRange || ""}</td>
                    <td>${difference}</td>
                </tr>
            `;
        });


        html += `
                </tbody>
            </table>
        `;


        document.getElementById(
            "myExaminations"
        ).innerHTML = html;
    }


    async function loadMyTreatments() {

        /*
         * This endpoint can be replaced with the
         * current logged-in user's ID endpoint.
         */

        const response =
            await fetch(
                contextPath +
                "/api/treatments/current"
            );

        if (!response.ok) {

            document.getElementById(
                "myTreatments"
            ).innerHTML =
                "Unable to load treatments.";

            return;
        }

        const treatments =
            await response.json();

        let html = `
            <table>
                <thead>
                    <tr>
                        <th>Treatment</th>
                        <th>Description</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                    </tr>
                </thead>
                <tbody>
        `;

        treatments.forEach(treatment => {

            html += `
                <tr>
                    <td>${treatment.treatmentName}</td>
                    <td>${treatment.description || ""}</td>
                    <td>${treatment.startDate}</td>
                    <td>${treatment.endDate}</td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>
        `;

        document.getElementById(
            "myTreatments"
        ).innerHTML = html;
    }


    loadDoctors();

</script>

</body>

</html>
