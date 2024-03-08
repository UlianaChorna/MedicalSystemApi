package com.uliana.MedicalSystemApi;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTestContainers {

    @Container
    protected static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
            .withDatabaseName("medicalSystem")
            .withUsername("root")
            .withPassword("root1945");

    private static void dataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }


}
