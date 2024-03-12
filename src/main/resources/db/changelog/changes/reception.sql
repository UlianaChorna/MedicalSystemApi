CREATE TABLE reception(
    data      datetime(6) ,
    doctor_id bigint     ,
    id        bigint auto_increment PRIMARY KEY,
    medicines VARCHAR(255),
    CONSTRAINT FOREIGN KEY  (doctor_id) REFERENCES medicalsystem.doctor (id)
);