package com.uliana.MedicalSystemApi.repository;

import com.uliana.MedicalSystemApi.entity.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DoctorRepositoryTest {
    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        doctorRepository.deleteAll();
    }

    @Test
    void findBySpecialty_ShouldReturnDoctorsWithMatchingSpecialty() {
        createAndPersistListOfDoctor();

        List<Doctor> actualDoctorDTOs = doctorRepository.findBySpecialty("Cardiologist");
        assertThat(actualDoctorDTOs).hasSize(2);
    }

    @Test
    void findBySpecialty_ShouldReturnEmptyListForNonExistingSpecialty() {
        createAndPersistListOfDoctor();

        List<Doctor> doctors = doctorRepository.findBySpecialty("Neurologistic");
        assertThat(doctors).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "Doctor John",
            "Doctor J",
            "Doctor",
            " John"
    })
    void getAllWithNameAndSpecialtyContains_ShouldReturnDoctorsWithMatchingNameAndSpecialty() {
        createAndPersistListOfDoctor();

        List<Doctor> doctors = doctorRepository.getAllWithNameAndSpecialtyContains("John", "Cardiologist");
        assertThat(doctors).hasSize(1);
        assertThat(doctors.getFirst().getSpecialty()).isEqualTo("Cardiologist");
    }

    @ParameterizedTest
    @CsvSource({
            " Doe",
            "Doctor John",
            "Smith",
            "John"
    })
    void getAllWithNameAndSpecialtyContains_ShouldReturnEmptyListForNonExistentNameAndSpecialty() {
        createAndPersistListOfDoctor();

        List<Doctor> doctors = doctorRepository.getAllWithNameAndSpecialtyContains("Jane", "Neurologist");
        assertThat(doctors).isEmpty();
    }


    private void createAndPersistListOfDoctor() {
        List<Doctor> doctors = List.of(
                new Doctor().setSpecialty("Cardiologist"),
                new Doctor().setName("Doctor John").setSpecialty("Cardiologist"),
                new Doctor().setName("Doctor Smith").setSpecialty("Neurologist"),
                new Doctor().setSpecialty("Neurologist")
        );
        doctorRepository.saveAll(doctors);
    }
}
