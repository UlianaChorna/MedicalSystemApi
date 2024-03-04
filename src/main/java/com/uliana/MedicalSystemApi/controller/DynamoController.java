package com.uliana.MedicalSystemApi.controller;

import com.uliana.MedicalSystemApi.aws.DynamoClient;
import com.uliana.MedicalSystemApi.dto.DynamoRecordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dynamo")
public class DynamoController {
    private final DynamoClient dynamoClient;

@PostMapping
    public void storeMessage(@RequestBody String message) {
    dynamoClient.storeMessage(message);
}

@GetMapping("/{uuid}")
    public DynamoRecordDTO getById(@PathVariable UUID uuid) {
    return dynamoClient.getById(uuid);
}

}
