package com.uliana.MedicalSystemApi.util;

public class ExceptionMessagesUtil {
    private static final String NOT_FOUND_MESSAGE = " with id '%s' not found";
    public static String PATIENT_FOUND_MESSAGE = "Patient".concat(NOT_FOUND_MESSAGE);
    public static String RECEPTION_FOUND_MESSAGE = "Reception".concat(NOT_FOUND_MESSAGE);
    public static String DOCTOR_FOUND_MESSAGE = "Doctor".concat(NOT_FOUND_MESSAGE);
}
