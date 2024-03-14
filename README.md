# Spring Boot Application for Medical System
## Overview
This is a Spring Boot application for managing a medical system. It provides RESTful APIs for creating, updating, and retrieving doctors, patients, and reception information.

### Configuration
The application configuration is done using the `application.yaml` file. The following configuration properties can be customized:

### Database Configuration:
`spring.datasource.username`: The username for the database (default is `root`).

`spring.datasource.password`: The password for the database (default is `root`).

## Liquibase

By following these steps, you can manually perform migration and rollback using Liquibase in your Spring Boot application.

### Manual Migration:
-Make sure you have Liquibase installed on your computer.

-You can install it from the official website or use dependency management tools like Maven.

-Create a changelog file for each type of database migration. For example in my project it is: `patient.sql`

-Update the db.changelog-master.yaml file to include your change log files.

-Execute Migration: Run the Liquibase command to execute the database migration. For example, using the command line or a script: liquibase --changeLogFile=db.changelog-master.yaml update . This will execute all changes from the change log files in the specified order.


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

## AWS S3 bucket
### Step 1: Create a S3 bucket
1. Log in to the AWS Console at [AWS](https://aws.amazon.com/).
2. Navigate to the S3 service.
3. Click on "Create bucket."

#### Step 2: Configure Access
1. In the AWS Console, navigate to the IAM service.
2. Create or select a role with access to S3.
3. Create access key.

### Upload file:
1. First you need set credentials in [application.yaml](src/main/resources/application.yaml) file.
2. Run application and use Postman for uploading file into your bucket.
3. Use a next API: http://localhost:8080/s3/upload, method `POST`.
4. Change `body` to `form-data`, `key` name like `file` and use custom `Content-Type`: `multipart/form-data; boundary=----WebKitFormBoundaryp7MA4YWxkTrZu0gW`