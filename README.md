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

# Screenshots
Main page:
- ![main_page](https://github.com/user-attachments/assets/bba190be-22f8-45b0-8673-66f667e1144f)

Prelogin page:
- ![prelogin_page](https://github.com/user-attachments/assets/06b6dea6-8268-44c4-8418-f30fc04cdff6)

LogIn page:
- ![login_page](https://github.com/user-attachments/assets/02e2da59-8415-40e0-a543-f3b4d48f978f)

Register page:
- ![reg_page](https://github.com/user-attachments/assets/911c5ed4-bc3a-48f0-84e1-d2ca8a2dc21c)

All cars page:
- ![cars_page](https://github.com/user-attachments/assets/18cd8fd3-e611-4e4d-b134-6a1add405d51)

Chosen car page:
- ![car_page](https://github.com/user-attachments/assets/90b5a2d6-ed8c-4a3f-95f5-b3de040ff837)

Rent page:
- ![rent_s_page](https://github.com/user-attachments/assets/c779dcef-3962-4399-922d-b96da4bffd3f)
  ![rent_d_page](https://github.com/user-attachments/assets/3a8d8662-0097-48a6-860a-abaa45faf723)
  ![rent_e_page](https://github.com/user-attachments/assets/83971076-1a95-4595-9e4c-2a7a73cb94e3)


User profile page:
- ![profile_page](https://github.com/user-attachments/assets/1f772fb2-5dfe-4917-9cfe-b545c92baf21)

Rents history page:
- ![rent_his_page](https://github.com/user-attachments/assets/9676feba-53c3-4729-a208-698c07a7dd12)

Admin profile page:
- ![admin_profile](https://github.com/user-attachments/assets/fd42c3f3-93a8-49ae-817d-84060e457b1c)

Cars administration page:
- ![car_edit](https://github.com/user-attachments/assets/7f417c35-1c20-46af-9b61-7132d2364fe6)

Single car info update page:
- ![car_update](https://github.com/user-attachments/assets/49841cc6-25cd-49ff-90cd-926f615f5849)

Car adding page:
- ![car_add1](https://github.com/user-attachments/assets/469fae98-870e-4076-a643-74f96eb06d6f)
  ![car_add2](https://github.com/user-attachments/assets/b4a78964-ab7f-475e-8e4f-16dd28840198)

Users administration page:
- ![user_edit](https://github.com/user-attachments/assets/42f95676-4760-4959-8fb5-0c4ab4393100)

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
