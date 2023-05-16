DROP TABLE IF EXISTS user, professional, patient;

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE professional (
    professional_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    name VARCHAR(50) NOT NULL,
    profession VARCHAR(50),
    work_location VARCHAR(255)
);

CREATE TABLE patient (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    name VARCHAR(50) NOT NULL,
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
VALUES (1, 'abc', 'a', 'UK');

INSERT INTO patient (user_id, name, gender, date_of_birth, phone_number, address)
VALUES (2, 'def', 'Male', '2022-1-1', '12345678', 'UK');