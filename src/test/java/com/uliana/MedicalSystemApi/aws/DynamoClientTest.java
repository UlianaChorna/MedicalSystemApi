package com.uliana.MedicalSystemApi.aws;

import com.uliana.MedicalSystemApi.dto.DynamoRecordDTO;
import com.uliana.MedicalSystemApi.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DynamoDbServiceTest {

    @Mock
    private DynamoDbClient dynamoDbClient;

    private DynamoClient dynamoClient;

    @BeforeEach
    void setUp() {
        dynamoClient = new DynamoClient(dynamoDbClient);
    }

    @Test
    void testGetById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put("uuid", AttributeValue.builder().s(uuid.toString()).build());
        itemMap.put("message", AttributeValue.builder().s("Test message").build());
        GetItemResponse getItemResponse = GetItemResponse.builder()
                .item(itemMap)
                .build();
        GetItemRequest expectedGetItemRequest = GetItemRequest.builder()
                .key(Collections.singletonMap("uuid", AttributeValue.builder().s(uuid.toString()).build()))
                .tableName("TestTable")
                .build();
        when(dynamoDbClient.getItem(expectedGetItemRequest)).thenReturn(getItemResponse);

        // Act
        DynamoRecordDTO result = dynamoClient.getById(uuid);

        // Assert
        assertEquals(uuid, result.getUuid());
        assertEquals("Test message", result.getMessage());
    }

    @Test
    void testGetByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        GetItemRequest expectedGetItemRequest = GetItemRequest.builder()
                .key(Collections.singletonMap("uuid", AttributeValue.builder().s(uuid.toString()).build()))
                .tableName("TestTable")
                .build();
        when(dynamoDbClient.getItem(expectedGetItemRequest)).thenReturn(GetItemResponse.builder().build());

        assertThrows(ResourceNotFoundException.class, () -> dynamoClient.getById(uuid));
    }
}
