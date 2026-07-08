-- Task 4 database. Run with: mysql -u root -p < db/schema.sql
CREATE DATABASE IF NOT EXISTS rmi_db;
USE rmi_db;

CREATE TABLE IF NOT EXISTS student_data (
    ID     INT PRIMARY KEY,
    NAME   VARCHAR(50)  NOT NULL,
    COURSE VARCHAR(20)  NOT NULL,
    SCORE  INT          NOT NULL,
    EMAIL  VARCHAR(100) NOT NULL
);

INSERT IGNORE INTO student_data (ID, NAME, COURSE, SCORE, EMAIL) VALUES
    (1, 'Jackline', 'BBIT', 85, 'jackie@gmail.com'),
    (2, 'Konni',    'ICS',  95, 'konni@gmail.com'),
    (3, 'Pamela',   'CNS',  90, 'pam123@gmail.com');
