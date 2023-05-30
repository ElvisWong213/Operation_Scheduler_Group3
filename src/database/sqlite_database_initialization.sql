DROP TABLE IF EXISTS diary;
DROP TABLE IF EXISTS appointment;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS professional;
DROP TABLE IF EXISTS patient;

-- user database
CREATE TABLE user (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE professional (
    professional_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER UNIQUE,
    name VARCHAR(50),
    profession VARCHAR(50),
    work_location VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE patient (
    patient_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER UNIQUE,
    name VARCHAR(50),
    gender VARCHAR(10),
    date_of_birth DATE,
    phone_number VARCHAR(50),
    address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

INSERT INTO user (email, password) VALUES 
('professional@gmail.com', 'abc123'),
('patient@gmail.com', 'def456');

INSERT INTO professional (user_id, name, profession, work_location)
VALUES (1, 'abc', 'Doctor', 'UK');

INSERT INTO patient (user_id, name, gender, date_of_birth, phone_number, address)
VALUES (2, 'def', 'Male', '2022-1-1', '12345678', 'UK');

-- appointment database
CREATE TABLE appointment (
    appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,
    date DATE,
    start_time TIME,
    end_time TIME,
    treatment_type VARCHAR(50),
    professional_id INTEGER,
    patient_id INTEGER,
    FOREIGN KEY (professional_id) REFERENCES professional(professional_id),
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id)
);

INSERT INTO appointment (date, start_time, end_time, treatment_type, professional_id, patient_id)
VALUES ('2023-05-30', '12:00:00', '13:00:00', 'Consultation', 1, 1);

-- diary database
CREATE TABLE diary (
    diary_id INTEGER PRIMARY KEY AUTOINCREMENT,
    date DATE,
    time TIME,
    note TEXT,
    professional_id INTEGER,
    patient_id INTEGER,
    FOREIGN KEY (professional_id) REFERENCES professional(professional_id),
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id)
);

INSERT INTO diary (date, time, note, professional_id, patient_id)
VALUES ('2023-05-30', '12:00:00', 'testing diary', 1, 1);
