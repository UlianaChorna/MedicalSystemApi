package com.uliana.MedicalSystemApi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class DynamoRecordDTO {
    private UUID uuid;
    private String message;
}
