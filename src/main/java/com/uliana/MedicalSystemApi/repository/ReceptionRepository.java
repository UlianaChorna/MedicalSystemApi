package com.uliana.MedicalSystemApi.repository;

import com.uliana.MedicalSystemApi.entity.Reception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReceptionRepository extends JpaRepository<Reception, Long> {
    Optional<Reception> findById(Long id);
    @Query(value = """
        SELECT r FROM Reception r
        WHERE r.medicines LIKE CONCAT('%', :medicinePart, '%')
    """)
    List<Reception> findAllByMedicinesContaining(@Param("medicinePart") String medicinePart);

    List<Reception> findAllByData(Date targetDate);
}
