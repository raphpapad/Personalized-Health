package com.medical.system.dao;

import com.medical.system.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User authenticate(
            String username,
            String password,
            String role
    ) {

        String sql = """
                SELECT *
                FROM users
                WHERE username = ?
                AND password = ?
                AND role = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                user.setAmka(rs.getString("amka"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setCertified(rs.getBoolean("certified"));
                user.setSpecialty(rs.getString("specialty"));
                user.setPhone(rs.getString("phone"));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean usernameExists(String username) {

        String sql =
                "SELECT id FROM users WHERE username = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean amkaExists(String amka) {

        String sql =
                "SELECT id FROM users WHERE amka = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, amka);

            ResultSet rs = statement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean registerUser(User user) {

        String sql = """
                INSERT INTO users
                (
                    username,
                    password,
                    first_name,
                    last_name,
                    date_of_birth,
                    amka,
                    email,
                    role,
                    certified
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, 'USER', TRUE)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setDate(5, user.getDateOfBirth());
            statement.setString(6, user.getAmka());
            statement.setString(7, user.getEmail());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean registerDoctor(User doctor) {

        String sql = """
                INSERT INTO users
                (
                    username,
                    password,
                    first_name,
                    last_name,
                    date_of_birth,
                    amka,
                    email,
                    role,
                    certified,
                    specialty,
                    phone
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, 'DOCTOR', FALSE, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, doctor.getUsername());
            statement.setString(2, doctor.getPassword());
            statement.setString(3, doctor.getFirstName());
            statement.setString(4, doctor.getLastName());
            statement.setDate(5, doctor.getDateOfBirth());
            statement.setString(6, doctor.getAmka());
            statement.setString(7, doctor.getEmail());
            statement.setString(8, doctor.getSpecialty());
            statement.setString(9, doctor.getPhone());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT *
                FROM users
                WHERE role <> 'ADMIN'
                ORDER BY created_at DESC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                user.setRole(rs.getString("role"));
                user.setCertified(rs.getBoolean("certified"));
                user.setSpecialty(rs.getString("specialty"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getPendingDoctors() {

        List<User> doctors = new ArrayList<>();

        String sql = """
                SELECT *
                FROM users
                WHERE role = 'DOCTOR'
                AND certified = FALSE
                ORDER BY created_at
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                User doctor = new User();

                doctor.setId(rs.getInt("id"));
                doctor.setUsername(rs.getString("username"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setDateOfBirth(rs.getDate("date_of_birth"));
                doctor.setAmka(rs.getString("amka"));
                doctor.setEmail(rs.getString("email"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setCertified(false);

                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public boolean certifyDoctor(int doctorId) {

        String sql = """
                UPDATE users
                SET certified = TRUE
                WHERE id = ?
                AND role = 'DOCTOR'
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, doctorId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteUser(int id) {

        String sql = """
                DELETE FROM users
                WHERE id = ?
                AND role <> 'ADMIN'
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUserById(int id) {

        String sql =
                "SELECT * FROM users WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                user.setAmka(rs.getString("amka"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setCertified(rs.getBoolean("certified"));
                user.setSpecialty(rs.getString("specialty"));
                user.setPhone(rs.getString("phone"));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateUser(User user) {

        String sql = """
                UPDATE users
                SET first_name = ?,
                    last_name = ?,
                    date_of_birth = ?,
                    email = ?
                WHERE id = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, user.getDateOfBirth());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateDoctor(User doctor) {

        String sql = """
                UPDATE users
                SET first_name = ?,
                    last_name = ?,
                    date_of_birth = ?,
                    email = ?,
                    specialty = ?,
                    phone = ?
                WHERE id = ?
                AND role = 'DOCTOR'
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setDate(3, doctor.getDateOfBirth());
            statement.setString(4, doctor.getEmail());
            statement.setString(5, doctor.getSpecialty());
            statement.setString(6, doctor.getPhone());
            statement.setInt(7, doctor.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
