package com.medical.system.dao;

import com.medical.system.model.Examination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExaminationDAO {

    public List<Examination> getPatientExaminations(
            int patientId
    ) {

        List<Examination> examinations = new ArrayList<>();

        String sql = """
                SELECT *
                FROM examinations
                WHERE patient_id = ?
                ORDER BY examination_date ASC, test_name ASC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, patientId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Examination examination = new Examination();

                examination.setId(rs.getInt("id"));
                examination.setPatientId(
                        rs.getInt("patient_id")
                );
                examination.setExaminationDate(
                        rs.getDate("examination_date")
                );
                examination.setTestName(
                        rs.getString("test_name")
                );
                examination.setValue(
                        rs.getDouble("value")
                );
                examination.setUnit(
                        rs.getString("unit")
                );
                examination.setReferenceRange(
                        rs.getString("reference_range")
                );
                examination.setNotes(
                        rs.getString("notes")
                );

                examinations.add(examination);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return examinations;
    }

    public boolean addExamination(
            Examination examination
    ) {

        String sql = """
                INSERT INTO examinations
                (
                    patient_id,
                    examination_date,
                    test_name,
                    value,
                    unit,
                    reference_range,
                    notes
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    examination.getPatientId()
            );

            statement.setDate(
                    2,
                    examination.getExaminationDate()
            );

            statement.setString(
                    3,
                    examination.getTestName()
            );

            statement.setDouble(
                    4,
                    examination.getValue()
            );

            statement.setString(
                    5,
                    examination.getUnit()
            );

            statement.setString(
                    6,
                    examination.getReferenceRange()
            );

            statement.setString(
                    7,
                    examination.getNotes()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
