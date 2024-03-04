package com.uliana.MedicalSystemApi.aws;

import com.uliana.MedicalSystemApi.dto.DynamoRecordDTO;
import com.uliana.MedicalSystemApi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DynamoClient {
    private final DynamoDbClient dynamoDbClient;
    private static final String TABLE_NAME = "TestTable";

    public void storeMessage(String message) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("uuid", AttributeValue.fromS(UUID.randomUUID().toString()));
        item.put("message", AttributeValue.fromS(message));

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        dynamoDbClient.putItem(putItemRequest);
    }

    public DynamoRecordDTO getById(UUID uuid) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("uuid", AttributeValue.fromS(uuid.toString()));
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .key(key)
                .tableName(TABLE_NAME)
                .build();
       GetItemResponse itemResponse = dynamoDbClient.getItem(getItemRequest);
       if( itemResponse.hasItem()) {
           return mapResponseToDO(itemResponse);
       }
       throw new ResourceNotFoundException("Record with uuid '%s' not fount in dynamo",uuid);
    

    }

    private DynamoRecordDTO mapResponseToDO(GetItemResponse itemResponse) {
        DynamoRecordDTO dto = new DynamoRecordDTO();

        Set<String> keys = itemResponse.item().keySet();
        for (String key: keys) {
            if(key.equals("uuid")) {
                dto.setUuid(UUID.fromString(itemResponse.item().get(key).s()));
            }
            if(key.equals("message")) {
                dto.setMessage(itemResponse.item().get(key).s());
            }
        }
        return dto;
    }
}
