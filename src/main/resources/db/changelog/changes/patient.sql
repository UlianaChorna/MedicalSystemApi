CREATE TABLE patient (
                         age INT,
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         gender VARCHAR(255),
                         name VARCHAR(255),
                         surname VARCHAR(255),
                         email VARCHAR(255),
                         password VARCHAR(255),
                         confirmed BIT
);