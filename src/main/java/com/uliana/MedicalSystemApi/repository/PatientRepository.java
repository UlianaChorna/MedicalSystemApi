package com.uliana.MedicalSystemApi.repository;

import com.uliana.MedicalSystemApi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findById(Long id);

    @Query(value = """
        SELECT p FROM Patient p
        WHERE p.age = 0
        AND p.name LIKE CONCAT('%', :namePart, '%')
    """)
    List<Patient> getAllWithZeroAgeAndNameContains(@Param("namePart") String namePart);
}
