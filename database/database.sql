CREATE DATABASE IF NOT EXISTS medical_system;

USE medical_system;

DROP TABLE IF EXISTS treatments;
DROP TABLE IF EXISTS examinations;
DROP TABLE IF EXISTS doctor_patients;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,

    username VARCHAR(50) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    date_of_birth DATE,

    amka VARCHAR(20) UNIQUE,

    email VARCHAR(150),

    role ENUM('USER', 'DOCTOR', 'ADMIN') NOT NULL,

    certified BOOLEAN DEFAULT FALSE,

    specialty VARCHAR(150),

    phone VARCHAR(30),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE doctor_patients (
    doctor_id INT NOT NULL,

    patient_id INT NOT NULL,

    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (doctor_id, patient_id),

    FOREIGN KEY (doctor_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    FOREIGN KEY (patient_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE examinations (
    id INT PRIMARY KEY AUTO_INCREMENT,

    patient_id INT NOT NULL,

    examination_date DATE NOT NULL,

    test_name VARCHAR(150) NOT NULL,

    value DECIMAL(10,2) NOT NULL,

    unit VARCHAR(50),

    reference_range VARCHAR(100),

    notes VARCHAR(500),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (patient_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE treatments (
    id INT PRIMARY KEY AUTO_INCREMENT,

    doctor_id INT NOT NULL,

    patient_id INT NOT NULL,

    treatment_name VARCHAR(255) NOT NULL,

    description TEXT,

    start_date DATE NOT NULL,

    end_date DATE NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (doctor_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    FOREIGN KEY (patient_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


-- =====================================================
-- ADMIN
-- username: admin
-- password: admin12*
-- =====================================================

INSERT INTO users
(
    username,
    password,
    first_name,
    last_name,
    role,
    certified
)
VALUES
(
    'admin',
    'admin12*',
    'System',
    'Administrator',
    'ADMIN',
    TRUE
);


-- =====================================================
-- SAMPLE USER
-- =====================================================

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
VALUES
(
    'john',
    'john123',
    'John',
    'Smith',
    '1990-05-15',
    '12345678901',
    'john@example.com',
    'USER',
    TRUE
);


-- =====================================================
-- SAMPLE CERTIFIED DOCTOR
-- =====================================================

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
VALUES
(
    'doctor1',
    'doctor123',
    'Michael',
    'Brown',
    '1980-03-10',
    '98765432101',
    'doctor@example.com',
    'DOCTOR',
    TRUE,
    'Cardiologist',
    '6900000000'
);


-- =====================================================
-- SAMPLE PENDING DOCTOR
-- =====================================================

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
VALUES
(
    'doctor2',
    'doctor123',
    'Anna',
    'Wilson',
    '1985-08-20',
    '55555555555',
    'anna@example.com',
    'DOCTOR',
    FALSE,
    'Dermatologist',
    '6911111111'
);


-- =====================================================
-- ASSIGN JOHN TO DOCTOR
-- =====================================================

INSERT INTO doctor_patients
(
    doctor_id,
    patient_id
)
SELECT
    d.id,
    p.id
FROM users d, users p
WHERE d.username = 'doctor1'
AND p.username = 'john';


-- =====================================================
-- SAMPLE EXAMINATIONS
-- =====================================================

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
SELECT
    id,
    '2026-01-10',
    'Glucose',
    95.00,
    'mg/dL',
    '70-100',
    'Normal'
FROM users
WHERE username = 'john';

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
SELECT
    id,
    '2026-03-10',
    'Glucose',
    110.00,
    'mg/dL',
    '70-100',
    'Slightly elevated'
FROM users
WHERE username = 'john';

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
SELECT
    id,
    '2026-01-10',
    'Cholesterol',
    190.00,
    'mg/dL',
    '<200',
    'Normal'
FROM users
WHERE username = 'john';

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
SELECT
    id,
    '2026-03-10',
    'Cholesterol',
    205.00,
    'mg/dL',
    '<200',
    'Slightly elevated'
FROM users
WHERE username = 'john';


-- =====================================================
-- SAMPLE TREATMENT
-- =====================================================

INSERT INTO treatments
(
    doctor_id,
    patient_id,
    treatment_name,
    description,
    start_date,
    end_date
)
SELECT
    d.id,
    p.id,
    'Healthy Diet',
    'Reduce saturated fats and increase physical activity.',
    '2026-01-01',
    '2026-12-31'
FROM users d, users p
WHERE d.username = 'doctor1'
AND p.username = 'john';
