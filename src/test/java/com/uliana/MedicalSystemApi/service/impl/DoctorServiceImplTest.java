package com.uliana.MedicalSystemApi.service.impl;

import com.uliana.MedicalSystemApi.dto.DoctorDTO;
import com.uliana.MedicalSystemApi.entity.Doctor;
import com.uliana.MedicalSystemApi.exception.ResourceNotFoundException;
import com.uliana.MedicalSystemApi.repository.DoctorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;
    @InjectMocks
    private DoctorServiceImpl doctorService;
    @Captor
    private ArgumentCaptor<Doctor> captor;

    private DoctorDTO dto;
    public static final String DOCTOR_NAME = "doctor";
    public static final String DOCTOR_SURNAME = "surnameDoctor";
    public static final String DOCTOR_SPECIALTY = "specialty";
    public static  final  Long DOCTOR_ID = 1L;



    @AfterEach
    void tearDown() {

    }

    @BeforeEach
    void setUp() {
        dto = prepareDoctorDTO();
    }

    @Test
    void create() {
        // Given
        Doctor expectDoctor = new Doctor()
                .setName(DOCTOR_NAME)
                .setSurname(DOCTOR_SURNAME)
                .setSpecialty(DOCTOR_SPECIALTY);


        // When
        doctorService.create(dto);

        // Then
        verify(doctorRepository).save(captor.capture());
        Doctor actualDoctor = captor.getValue();
        assertThat(actualDoctor.getName()).isEqualTo(expectDoctor.getName());
        assertThat(actualDoctor.getSurname()).isEqualTo(expectDoctor.getSurname());
        assertThat(actualDoctor.getSpecialty()).isEqualTo(expectDoctor.getSpecialty());
    }

    private static DoctorDTO prepareDoctorDTO() {
        return new DoctorDTO()
                .setName(DOCTOR_NAME)
                .setSurname(DOCTOR_SURNAME)
                .setSpecialty(DOCTOR_SPECIALTY);
    }

    @Test
    void getById() {
        //Given
        dto.setId(DOCTOR_ID);
        Doctor doctor = new Doctor()
                .setId(DOCTOR_ID)
                .setName(DOCTOR_NAME)
                .setSurname(DOCTOR_SURNAME)
                .setSpecialty(DOCTOR_SPECIALTY);

        //When
        when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
        //Then
        DoctorDTO actualDoctor = doctorService.getById(DOCTOR_ID);
        assertThat(actualDoctor).isEqualTo(dto);
    }

    @Test
    void getById_shouldThrow(){
        //Given
        dto.setId(DOCTOR_ID);

        //When
        when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.empty());
        //Then
        assertThrows(ResourceNotFoundException.class, () -> doctorService.getById(DOCTOR_ID));
    }
}