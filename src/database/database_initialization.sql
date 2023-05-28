DROP TABLE IF EXISTS diary, appointment, user, professional, patient;

-- user database
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE professional (
    professional_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    name VARCHAR(50),
    profession VARCHAR(50),
    work_location VARCHAR(255)
);

CREATE TABLE patient (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    name VARCHAR(50),
    gender VARCHAR(10),
    date_of_birth DATE,
    phone_number VARCHAR(50),
    address VARCHAR(255)
);

ALTER TABLE professional
ADD CONSTRAINT fk_professional_user_id
FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE patient
ADD CONSTRAINT fk_patient_user_id
FOREIGN KEY (user_id) REFERENCES user(user_id);

INSERT INTO user (email, password) VALUES 
('professional@gmail.com', 'abc123'),
('patient@gmail.com', 'def456');

INSERT INTO professional (user_id, name, profession, work_location)
VALUES (1, 'abc', 'Doctor', 'UK');

INSERT INTO patient (user_id, name, gender, date_of_birth, phone_number, address)
VALUES (2, 'def', 'Male', '2022-1-1', '12345678', 'UK');

-- appointment database
CREATE TABLE appointment (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    start_time TIME,
    end_time TIME,
    treatment_type VARCHAR(50),
    professional_id INT,
    patient_id INT
);

ALTER TABLE appointment
ADD CONSTRAINT fk_appointment_professional_id
FOREIGN KEY (professional_id) REFERENCES professional(professional_id);

ALTER TABLE appointment
ADD CONSTRAINT fk_appointment_patient_id
FOREIGN KEY (patient_id) REFERENCES patient(patient_id);

INSERT INTO appointment (date, start_time, end_time, treatment_type, professional_id, patient_id)
VALUES ('2023-05-30', '12:00:00', '13:00:00', 'Consultation', 1, 1);


-- diary database
CREATE TABLE diary (
    diary_id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    time TIME,
    note MEDIUMTEXT,
    professional_id INT,
    patient_id INT
);

ALTER TABLE diary
ADD CONSTRAINT fk_diary_professional_id
FOREIGN KEY (professional_id) REFERENCES professional(professional_id);

ALTER TABLE diary
ADD CONSTRAINT fk_diary_patient_id
FOREIGN KEY (patient_id) REFERENCES patient(patient_id);

INSERT INTO diary (date, time, note, professional_id, patient_id)
VALUES ('2023-05-30', '12:00:00', 'testing diary', 1, 1);
