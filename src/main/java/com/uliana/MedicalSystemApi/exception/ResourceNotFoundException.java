package com.uliana.MedicalSystemApi.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message, Object... fromArgs) {
        super(message.formatted(fromArgs));
    }
}
