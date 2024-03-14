
CREATE TABLE IF NOT EXISTS reception_patient (
    reception_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    PRIMARY KEY (reception_id, patient_id),
    FOREIGN KEY (reception_id) REFERENCES reception (id) ,
    FOREIGN KEY (patient_id) REFERENCES patient (id)
);

-- rollback DROP TABLE reception_patient;