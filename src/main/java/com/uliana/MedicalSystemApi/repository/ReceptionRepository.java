package com.uliana.MedicalSystemApi.repository;

import com.uliana.MedicalSystemApi.entity.Reception;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceptionRepository extends JpaRepository<Reception, Long> {
    Optional<Reception> findById(Long id);
}
