package com.uliana.MedicalSystemApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uliana.MedicalSystemApi.dto.DoctorDTO;
import com.uliana.MedicalSystemApi.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;
    private MockMvc mockMvc;
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doctorController = new DoctorController(doctorService);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    void createDoctor() throws Exception {
        DoctorDTO doctorDTO = prepareDto();

        when(doctorService.create(any(DoctorDTO.class))).thenReturn(doctorDTO);

        mockMvc.perform(post("/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(doctorDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.specialty").value("testSpeciality"));

        verify(doctorService).create(any(DoctorDTO.class));
    }

    @Test
    void getById() throws Exception {
        DoctorDTO doctorDTO = prepareDto();

        when(doctorService.getById(1L)).thenReturn(doctorDTO);

        mockMvc.perform(get("/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.specialty").value("testSpeciality"));

        verify(doctorService).getById(1L);
    }

    @Test
    void testEqualsAndHashCode() {
        DoctorDTO doctor1 = prepareDto();
        DoctorDTO doctor2 = prepareDto();

        // Test equals
        assertTrue(doctor1.equals(doctor2));
        assertTrue(doctor2.equals(doctor1));

        // Test hashCode
        assertEquals(doctor1.hashCode(), doctor2.hashCode());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DoctorDTO prepareDto() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(1L);
        doctorDTO.setName("Jane");
        doctorDTO.setSurname("Smith");
        doctorDTO.setSpecialty("testSpeciality");

        return doctorDTO;
    }
}
