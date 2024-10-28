SHOW DATABASES;

CREATE DATABASE disaster_detection;

CREATE USER 'dev'@'localhost' IDENTIFIED BY 'qqwwee11@@';
GRANT ALL PRIVILEGES ON *.* TO 'dev'@'localhost' WITH GRANT OPTION;

USE disaster_detection;

SHOW TABLES;

DESC image_data;

CREATE TABLE image_data(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    timestamp TIMESTAMP,
    token VARCHAR(30) NOT NULL,
    label INT NOT NULL,
    percent DOUBLE NOT NULL
);

CREATE TABLE chat(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    timestamp TIMESTAMP,
    ip VARCHAR(30),
    text TEXT
);

DROP TABLE image_data;