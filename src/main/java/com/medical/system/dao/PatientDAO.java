package com.medical.system.dao;

import com.medical.system.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public List<Patient> getAllPatients() {

        List<Patient> patients = new ArrayList<>();

        String sql = """
                SELECT id,
                       username,
                       first_name,
                       last_name,
                       date_of_birth,
                       amka,
                       email
                FROM users
                WHERE role = 'USER'
                ORDER BY last_name
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

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
}
