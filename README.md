# Spring Boot Application for Medical System
## Overview
This is a Spring Boot application for managing a medical system. It provides RESTful APIs for creating, updating, and retrieving doctors, patients, and reception information.

### Configuration
The application configuration is done using the `application.yaml` file. The following configuration properties can be customized:

### Database Configuration:
`spring.datasource.username`: The username for the database (default is `root`).

`spring.datasource.password`: The password for the database (default is `root`).

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
