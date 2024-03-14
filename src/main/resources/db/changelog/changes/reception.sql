CREATE TABLE IF NOT EXISTS TABLE reception(
                          data      datetime(6),
                          doctor_id bigint,
                          id        bigint auto_increment PRIMARY KEY,
                          medicines VARCHAR(255),
                          CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (id)
);

-- rollback DROP TABLE reception;