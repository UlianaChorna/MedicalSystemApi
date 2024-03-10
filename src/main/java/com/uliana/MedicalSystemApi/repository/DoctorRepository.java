package com.uliana.MedicalSystemApi.repository;

import com.uliana.MedicalSystemApi.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findById(Long id);
    List<Doctor> findBySpecialty(String specialty);

    @Query(value = """
        SELECT d FROM Doctor d
        WHERE d.name LIKE CONCAT('%', :namePart, '%')
        AND d.specialty LIKE CONCAT('%', :specialtyPart, '%')
    """)
    List<Doctor> getAllWithNameAndSpecialtyContains(@Param("namePart") String namePart, @Param("specialtyPart") String specialtyPart);
}
