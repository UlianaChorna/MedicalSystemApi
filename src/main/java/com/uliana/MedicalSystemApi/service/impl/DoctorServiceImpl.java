package com.uliana.MedicalSystemApi.service.impl;

import com.uliana.MedicalSystemApi.dto.DoctorDTO;
import com.uliana.MedicalSystemApi.entity.Doctor;
import com.uliana.MedicalSystemApi.exception.ResourceNotFoundException;
import com.uliana.MedicalSystemApi.repository.DoctorRepository;
import com.uliana.MedicalSystemApi.service.DoctorService;
import com.uliana.MedicalSystemApi.util.DoctorDTOMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.uliana.MedicalSystemApi.util.ExceptionMessagesUtil.DOCTOR_FOUND_MESSAGE;

@RequiredArgsConstructor
@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {
    public final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public DoctorDTO create(DoctorDTO dto) {
        Doctor doctor = DoctorDTOMapperUtil.mapFromDTO(dto);
        doctor = doctorRepository.save(doctor);
        return DoctorDTOMapperUtil.mapToDTO(doctor);
    }


    @Override
    public DoctorDTO getById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(DOCTOR_FOUND_MESSAGE, id)
        );
        return DoctorDTOMapperUtil.mapToDTO(doctor);
    }
}
