CREATE TABLE IF NOT EXISTS TABLE doctor (
    id  bigint auto_increment PRIMARY KEY,
    name      VARCHAR(255) ,
    specialty VARCHAR(255) ,
    surname   VARCHAR(255)
);
-- rollback DROP TABLE doctor;