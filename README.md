# Spring Boot Application for Medical System
## Overview
This is a Spring Boot application for managing a medical system. It provides RESTful APIs for creating, updating, and retrieving doctors, patients, and reception information.

### Configuration
The application configuration is done using the `application.yaml` file. The following configuration properties can be customized:

### Database Configuration:
`spring.datasource.username`: The username for the database (default is `root`).

`spring.datasource.password`: The password for the database (default is `root`).
### Liquibase Setup
Liquibase is integrated into the application for managing database changes. Here's how you can set it up:

`liquibase.properties`:
```  
spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.yaml
```  
To run scripts with Liquibase, you can create `sql` file with changes and update `db.changelog-master.yaml`
#### Example:
1)`src/main/resources/db/changelog/changes/myFile.sql`
2)`db.changelog-master.yaml`:
``` 
databaseChangeLog:
  - include:
      file: classpath:/db/changelog/changes/myFile.sql
```

### Email Sending Configuration
To enable email functionality in the application, you can configure the following properties in the `application.yaml` file:
``` 
  mail:
    host: smtp.gmail.com
    port: 587
    username: email
    password: password
    properties:
      mail:
        smtp:
          auth: true
          starttls.enable: true
``` 
Replace `username` with your Gmail address and `password` with your Gmail password. It's recommended to use environment variables or secure storage for sensitive information like passwords.

### Running the Application:
To run the application, you can use Maven. Navigate to the project directory and execute the following command:

`mvn spring-boot:run`
### Docker Setup
To run the application using Docker, you can use the following Docker Compose configuration:

`docker-compose.yml`
```  yaml
version: '3.8'

services:
  db:
    image: mysql
    container_name: ll7medicalsystemapi-db
    restart: always
    environment:
          MYSQL_ROOT_PASSWORD: root
          MYSQL_DATABASE: medicalSystem
    volumes:
        - ./dbdata:/var/lib/mysql
    ports:
      - 3306:3306
```  

 ### Running the Application with Docker Compose
1) Create a `docker-compose.yml` file with the above configuration.
2) Run the following command to start the application:
``` bash
docker-compose up
```
1) The application will start, and you can access the APIs using tools like Postman or curl.
 #### Note
Make sure to customize the database configuration in the application.yaml file according to your environment.
### Swagger Configuration:
Swagger UI is integrated into the application for easy API testing and documentation. You can access the Swagger UI at the following URL when the application is running:
`http://localhost:8080/swagger-ui/index.html#/`