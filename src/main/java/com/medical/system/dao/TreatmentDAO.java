package com.medical.system.dao;

import com.medical.system.model.Treatment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {

    public boolean addTreatment(
            Treatment treatment
    ) {

        String sql = """
                INSERT INTO treatments
                (
                    doctor_id,
                    patient_id,
                    treatment_name,
                    description,
                    start_date,
                    end_date
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    treatment.getDoctorId()
            );

            statement.setInt(
                    2,
                    treatment.getPatientId()
            );

            statement.setString(
                    3,
                    treatment.getTreatmentName()
            );

            statement.setString(
                    4,
                    treatment.getDescription()
            );

            statement.setDate(
                    5,
                    treatment.getStartDate()
            );

            statement.setDate(
                    6,
                    treatment.getEndDate()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Treatment> getActiveTreatments(
            int patientId
    ) {

        List<Treatment> treatments = new ArrayList<>();

        String sql = """
                SELECT *
                FROM treatments
                WHERE patient_id = ?
                AND CURDATE()
                    BETWEEN start_date AND end_date
                ORDER BY start_date DESC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, patientId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Treatment treatment = new Treatment();

                treatment.setId(rs.getInt("id"));
                treatment.setDoctorId(
                        rs.getInt("doctor_id")
                );
                treatment.setPatientId(
                        rs.getInt("patient_id")
                );
                treatment.setTreatmentName(
                        rs.getString("treatment_name")
                );
                treatment.setDescription(
                        rs.getString("description")
                );
                treatment.setStartDate(
                        rs.getDate("start_date")
                );
                treatment.setEndDate(
                        rs.getDate("end_date")
                );

                treatments.add(treatment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treatments;
    }

    public List<Treatment> getAllTreatments(
            int patientId
    ) {

        List<Treatment> treatments = new ArrayList<>();

        String sql = """
                SELECT *
                FROM treatments
                WHERE patient_id = ?
                ORDER BY start_date DESC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, patientId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Treatment treatment = new Treatment();

                treatment.setId(rs.getInt("id"));
                treatment.setDoctorId(
                        rs.getInt("doctor_id")
                );
                treatment.setPatientId(
                        rs.getInt("patient_id")
                );
                treatment.setTreatmentName(
                        rs.getString("treatment_name")
                );
                treatment.setDescription(
                        rs.getString("description")
                );
                treatment.setStartDate(
                        rs.getDate("start_date")
                );
                treatment.setEndDate(
                        rs.getDate("end_date")
                );

                treatments.add(treatment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treatments;
    }
}
