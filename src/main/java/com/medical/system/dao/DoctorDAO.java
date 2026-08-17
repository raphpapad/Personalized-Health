package com.medical.system.dao;

import com.medical.system.model.Doctor;
import com.medical.system.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public List<Doctor> getCertifiedDoctors() {

        List<Doctor> doctors = new ArrayList<>();

        String sql = """
                SELECT id,
                       username,
                       first_name,
                       last_name,
                       specialty,
                       email,
                       phone
                FROM users
                WHERE role = 'DOCTOR'
                AND certified = TRUE
                ORDER BY last_name
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Doctor doctor = new Doctor();

                doctor.setId(rs.getInt("id"));
                doctor.setUsername(rs.getString("username"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPhone(rs.getString("phone"));

                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public boolean assignPatientToDoctor(
            int doctorId,
            int patientId
    ) {

        String sql = """
                INSERT IGNORE INTO doctor_patients
                (doctor_id, patient_id)
                VALUES (?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Patient> getPatientsForDoctor(int doctorId) {

        List<Patient> patients = new ArrayList<>();

        String sql = """
                SELECT
                    u.id,
                    u.username,
                    u.first_name,
                    u.last_name,
                    u.date_of_birth,
                    u.amka,
                    u.email
                FROM users u
                INNER JOIN doctor_patients dp
                    ON u.id = dp.patient_id
                WHERE dp.doctor_id = ?
                ORDER BY u.last_name
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, doctorId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Patient patient = new Patient();

                patient.setId(rs.getInt("id"));
                patient.setUsername(rs.getString("username"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setDateOfBirth(
                        rs.getDate("date_of_birth")
                );
                patient.setAmka(rs.getString("amka"));
                patient.setEmail(rs.getString("email"));

                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public boolean isDoctorAssignedToPatient(
            int doctorId,
            int patientId
    ) {

        String sql = """
                SELECT *
                FROM doctor_patients
                WHERE doctor_id = ?
                AND patient_id = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);

            ResultSet rs = statement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
