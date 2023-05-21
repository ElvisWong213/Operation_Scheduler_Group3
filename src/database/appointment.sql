DROP TABLE IF EXISTS appointment;

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
VALUES ('2023-05-19', '12:00:00', '13:00:00', 'Consultation', 1, 1);