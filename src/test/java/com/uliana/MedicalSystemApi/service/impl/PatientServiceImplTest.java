package com.uliana.MedicalSystemApi.service.impl;

import com.uliana.MedicalSystemApi.dto.PatientDTO;
import com.uliana.MedicalSystemApi.entity.Patient;
import com.uliana.MedicalSystemApi.entity.Reception;
import com.uliana.MedicalSystemApi.exception.ResourceNotFoundException;
import com.uliana.MedicalSystemApi.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {
    @Mock
    private PatientRepository patientRepository;
    @InjectMocks
    private PatientServiceImpl patientService;
    @Captor
    private ArgumentCaptor<Patient> captor;
    private PatientDTO dto;
    public static final String PATIENT_NAME = "patient";
    public static final String PATIENT_SURNAME = "surnamePatient";
    public static final String PATIENT_GENDER = "gender";
    public static  final  Long PATIENT_ID = 1L;

    @BeforeEach
    void setUp() {
        dto = preparePatientDTO();
    }

    @Test
    void create() {
        // Given
        Patient expectPatient = new Patient()
                .setName(PATIENT_NAME)
                .setSurname(PATIENT_SURNAME)
                .setGender(PATIENT_GENDER)
                .setAge(10);

        // When
        patientService.create(dto);

        // Then
        verify(patientRepository).save(captor.capture());
        Patient actualPatient = captor.getValue();
        assertThat(actualPatient.getName()).isEqualTo(expectPatient.getName());
        assertThat(actualPatient.getSurname()).isEqualTo(expectPatient.getSurname());
        assertThat(actualPatient.getGender()).isEqualTo(expectPatient.getGender());
        assertThat(actualPatient.getAge()).isEqualTo(expectPatient.getAge());
        assertThat(actualPatient.getReception()).isEqualTo(expectPatient.getReception());
    }

    @Test
    void getById() {
        //Given
        dto.setId(PATIENT_ID);
        Patient patient = new Patient()
                .setId(PATIENT_ID)
                .setName(PATIENT_NAME)
                .setSurname(PATIENT_SURNAME)
                .setGender(PATIENT_GENDER)
                .setAge(10)
                .setReception(new HashSet<>());

        //When
        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));
        //Then
        PatientDTO actualPatient = patientService.getById(PATIENT_ID);
        assertThat(actualPatient).isEqualTo(dto);
    }

    @Test
    void getById_shouldThrow(){
        //Given
        dto.setId(PATIENT_ID);

        //When
        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());
        //Then
        assertThrows(ResourceNotFoundException.class, () -> patientService.getById(PATIENT_ID));
    }

    private static PatientDTO preparePatientDTO() {
        return new PatientDTO()
                .setName(PATIENT_NAME)
                .setAge(10)
                .setSurname(PATIENT_SURNAME)
                .setGender(PATIENT_GENDER)
                .setReceptions(new HashSet<>());
    }
}