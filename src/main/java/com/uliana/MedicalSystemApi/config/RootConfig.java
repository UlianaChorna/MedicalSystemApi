package com.uliana.MedicalSystemApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class RootConfig {
    @Bean
    public DynamoDbClient DynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.EU_NORTH_1)
                .build();
    }
}
