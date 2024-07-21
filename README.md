# Carsharing App

# Authors
https://github.com/Cn1d3

# Description
The demonstration of components of the carsharing information system for use in business management and operation of the carsharing service. 
Include: 
- posibilities of authentication and profile management;
- Leaflet API map used for parkings search;
- car choice and filtering;
- investigating car parameters
- creating new rents;
- counting and monitiring rent stats;
- getting the result of a rent and it`s params;
- rental history view;
- car administartion;
- user administartion;
  
# App Launch

Requirements:
- JDK 19.0+
- My SQL Workbench
- Intelij Idea IDE
- Any Browser
  
Installation sequence:
1. To run the system locally, the user must first launch the MySQL Workbench application and create a database called carsharing, then add the code to create the tables that participate in the application.
CREATE DATABASE carsharing;
CREATE TABLE parking (
    id INT PRIMARY KEY AUTO_INCREMENT,
    address VARCHAR(255) NOT NULL,
    length FLOAT,
    width FLOAT
);
CREATE TABLE car (
    id INT PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    class VARCHAR(255) NOT NULL,
    is_available BOOL,
    image varchar(1000),
    parking_id INT,
    FOREIGN KEY (parking_id) REFERENCES parking(id)
);
CREATE TABLE driver (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE rent (
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_point VARCHAR(255),
    end_point VARCHAR(255),
    start_time DATE,
    end_time DATE,
    total_cost INT,
    driver_id INT,
    FOREIGN KEY (driver_id) REFERENCES driver(id),
    ordered_car_id INT,
    FOREIGN KEY (ordered_car_id) REFERENCES car(id)
);
CREATE TABLE image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    orig_name VARCHAR(255),
    size BIGINT,
    content_type VARCHAR(255),
    preview TINYINT(1),
    bytes longblob,
    car_id INT,
    driver_id INT,
    CONSTRAINT fk_car_id FOREIGN KEY (car_id) REFERENCES car(id), 
    CONSTRAINT fk_driver_id FOREIGN KEY (driver_id) REFERENCES driver(id)
);
2. After that, you need to open Intelij Idea Ultimate and open the pre-extracted “carsharing” project, wait for the project to build and connect all libraries and dependencies. When the process is complete, click the “Run” button to launch the application. 
Next, go to any browser and follow the link: http://localhost:8080/. The main page of the application should open, where you can start interacting with the application.
