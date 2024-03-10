package com.uliana.MedicalSystemApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uliana.MedicalSystemApi.dto.ReceptionDTO;
import com.uliana.MedicalSystemApi.service.ReceptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ReceptionControllerTest {
    @Mock
    private ReceptionService receptionService;
    private MockMvc mockMvc;
    private ReceptionController receptionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        receptionController = new ReceptionController(receptionService);
        mockMvc = MockMvcBuilders.standaloneSetup(receptionController).build();
    }

    @Test
    void createReception() throws Exception {
        ReceptionDTO receptionDTO = prepareDto();

        when(receptionService.create(any(ReceptionDTO.class))).thenReturn(receptionDTO);

        mockMvc.perform(post("/reception")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(receptionDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doctorId").value(1))
                .andExpect(jsonPath("$.medicines").value("medicines"));

        verify(receptionService).create(any(ReceptionDTO.class));
    }

    @Test
    void getById() throws Exception {
        ReceptionDTO receptionDTO = prepareDto();

        when(receptionService.getById(1L)).thenReturn(receptionDTO);

        mockMvc.perform(get("/reception/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doctorId").value(1))
                .andExpect(jsonPath("$.medicines").value("medicines"));

        verify(receptionService).getById(1L);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ReceptionDTO prepareDto() {
        ReceptionDTO receptionDTO = new ReceptionDTO();
        receptionDTO.setId(1L);
        receptionDTO.setDoctorId(1L);
        receptionDTO.setPatients(List.of(1L));
        receptionDTO.setMedicines("medicines");
        receptionDTO.setDate(new Date());

        return receptionDTO;
    }
}


