package com.uliana.MedicalSystemApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uliana.MedicalSystemApi.dto.PatientDTO;
import com.uliana.MedicalSystemApi.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {
    @Mock
    private PatientService patientService;
    private MockMvc mockMvc;
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientController = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void createPatient() throws Exception {
        PatientDTO patientDTO = prepareDto();

        when(patientService.create(any(PatientDTO.class))).thenReturn(patientDTO);

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.name").value("John"));

        verify(patientService).create(any(PatientDTO.class));
    }

    @Test
    void getById() throws Exception {
        PatientDTO patientDTO = prepareDto();

        when(patientService.getById(1L)).thenReturn(patientDTO);

        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.name").value("John"));

        verify(patientService).getById(1L);
    }

    @Test
    void testEqualsAndHashCode() {
        PatientDTO patient1 = prepareDto();
        PatientDTO patient2 = prepareDto();

        // Test equals
        assertTrue(patient1.equals(patient2));
        assertTrue(patient2.equals(patient1));

        // Test hashCode
        assertEquals(patient1.hashCode(), patient2.hashCode());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PatientDTO prepareDto() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setName("John");
        patientDTO.setSurname("Smith");
        patientDTO.setGender("M");
        patientDTO.setAge(25);

        return patientDTO;
    }
}

