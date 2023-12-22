-- init.sql

-- Create a database if it doesn't exist
CREATE DATABASE IF NOT EXISTS smartwateringV2;

-- Use the created database
USE smartwateringV2;

-- Create a table for users
CREATE TABLE IF NOT EXISTS app_user (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL
);

-- Insert some initial data
INSERT INTO app_user (username, password) VALUES
                                           ('admin', '$2a$10$SomdbSoNy3jQRr4JRsB9Buo7p419yN02Z2xjv67ErVd/EuSWoG3Cy');

