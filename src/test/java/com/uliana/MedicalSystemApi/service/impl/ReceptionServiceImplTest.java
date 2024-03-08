package com.uliana.MedicalSystemApi.service.impl;

import com.uliana.MedicalSystemApi.dto.ReceptionDTO;
import com.uliana.MedicalSystemApi.entity.Doctor;
import com.uliana.MedicalSystemApi.entity.Patient;
import com.uliana.MedicalSystemApi.entity.Reception;
import com.uliana.MedicalSystemApi.exception.ResourceNotFoundException;
import com.uliana.MedicalSystemApi.repository.DoctorRepository;
import com.uliana.MedicalSystemApi.repository.PatientRepository;
import com.uliana.MedicalSystemApi.repository.ReceptionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceptionServiceImplTest {
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private ReceptionRepository receptionRepository;
    @InjectMocks
    private ReceptionServiceImpl receptionService;
    @Captor
    private ArgumentCaptor<Reception> captor;

    private ReceptionDTO dto;
    private static final String RECEPTION_MEDICINES = "medicines";
    private static final Date RECEPTION_DATE = new Date();
    private  static  final  Long RECEPTION_ID = 1L;
    private  static  final  Long DOCTOR_ID = 1L;




    @AfterEach
    void tearDown() {

    }

    @BeforeEach
    void setUp() {
        dto = prepareReceptionDTO();
    }

    @Test
    void create() {
        // Given


        // When
        when(doctorRepository.findById(dto.getDoctorId())).thenReturn(Optional.of(new Doctor()));
        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(receptionRepository.save(any())).thenAnswer(invocation -> {
            Reception savedReception = invocation.getArgument(0);
            savedReception.setId(RECEPTION_ID);
            return savedReception;
        });
        ReceptionDTO resultDTO = receptionService.create(dto);

        verify(receptionRepository).save(captor.capture());
        Reception savedReception = captor.getValue();
        // Then
        assertNotNull(resultDTO);
        assertNotNull(savedReception);
        assertEquals(RECEPTION_ID, resultDTO.getId()); // Assuming getId() method exists in ReceptionDTO
        verify(doctorRepository).findById(dto.getDoctorId());
        verify(patientRepository, times(dto.getPatients().size())).findById(any());
        verify(receptionRepository).save(any());
    }


    private static ReceptionDTO prepareReceptionDTO() {
        return new ReceptionDTO()
                .setId(RECEPTION_ID)
                .setDate(RECEPTION_DATE)
                .setMedicines(RECEPTION_MEDICINES)
                .setDoctorId(DOCTOR_ID)
                .setPatients(List.of(1L));
    }

    @Test
    void testGetPatients() {
        // Given
        List<Long> patientIdList = Arrays.asList(1L, 2L, 3L);

        Patient patient1 = new Patient();
        patient1.setId(1L);

        Patient patient2 = new Patient();
        patient2.setId(2L);

        Patient patient3 = new Patient();
        patient3.setId(3L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient2));
        when(patientRepository.findById(3L)).thenReturn(Optional.of(patient3));

        // When
        Set<Patient> patients = receptionService.getPatients(patientIdList);

        // Then
        assertThat(patients).containsExactlyInAnyOrder(patient1, patient2, patient3);
    }

    @Test
    void getById() {
        //Given
        dto.setId(RECEPTION_ID);
        Reception reception = new Reception()
                .setId(RECEPTION_ID)
                .setData(RECEPTION_DATE)
                .setMedicines(RECEPTION_MEDICINES);
        Doctor doctor = new Doctor(); // Ініціалізуємо об'єкт Doctor
        doctor.setId(DOCTOR_ID); // Встановлюємо ідентифікатор для об'єкта Doctor
        reception.setDoctor(doctor); // Встановлюємо об'єкт Doctor у Reception

        //When
        when(receptionRepository.findById(RECEPTION_ID)).thenReturn(Optional.of(reception));
        //Then
        ReceptionDTO actualReception = receptionService.getById(RECEPTION_ID);
        assertThat(actualReception.getId()).isEqualTo(dto.getId());
        assertThat(actualReception.getDate()).isEqualTo(dto.getDate());
        assertThat(actualReception.getMedicines()).isEqualTo(dto.getMedicines());
    }

    @Test
    void getById_shouldThrow(){
        //Given
        dto.setId(RECEPTION_ID);

        //When
        when(receptionRepository.findById(RECEPTION_ID)).thenReturn(Optional.empty());
        //Then
        assertThrows(ResourceNotFoundException.class, () -> receptionService.getById(RECEPTION_ID));
    }

    @Test
    void addPatientToReception_ReceptionNotFound() {
        Long receptionId = 1L;
        Long patientId = 2L;

        when(receptionRepository.findById(receptionId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            receptionService.addPatientToReception(receptionId, patientId);
        });
    }

    @Test
    void addPatientToReception_PatientNotFound() {
        Long receptionId = 1L;
        Long patientId = 2L;

        Reception reception = new Reception();
        reception.setId(receptionId);

        when(receptionRepository.findById(receptionId)).thenReturn(Optional.of(reception));
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            receptionService.addPatientToReception(receptionId, patientId);
        });
    }

    @Test
    void addPatientToReception() {
        // Given
        Long receptionId = 1L;
        Long patientId = 2L;

        Reception reception = new Reception();
        reception.setId(receptionId);
        Set<Patient> patients = new HashSet<>();
        reception.setPatient(patients);

        Patient patient = new Patient();
        patient.setId(patientId);

        when(receptionRepository.findById(receptionId)).thenReturn(Optional.of(reception));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(receptionRepository.save(any(Reception.class))).then(AdditionalAnswers.returnsFirstArg());

        // When
        ReceptionDTO result = receptionService.addPatientToReception(receptionId, patientId);

        // Then
        verify(receptionRepository).save(reception);
        assertThat(reception.getPatient()).contains(patient);
        assertThat(result).isNotNull();
        // Add additional assertions if needed
    }
}