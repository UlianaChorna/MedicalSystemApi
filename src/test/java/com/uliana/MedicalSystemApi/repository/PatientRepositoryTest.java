package com.uliana.MedicalSystemApi.repository;

import com.uliana.MedicalSystemApi.entity.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {
    @Autowired

    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @CsvSource({
            "Tom",
            " Tom",
            "t Tom",
            "nt Tom",
            "ent Tom",
            "ient Tom",
            "tient Tom",
            "atient Tom",
            "Patient Tom"
    })
    void getAllWithZeroAgeAndNameContains(String namePart) {
        createAndPersistListOfPatient();

        List<Patient> allPatients = patientRepository.findAll();
        assertThat(allPatients).hasSize(5);

        List<Patient> actualPatientList = patientRepository.getAllWithZeroAgeAndNameContains(namePart);
        assertThat(actualPatientList).hasSize(1);
    }

    @ParameterizedTest
    @CsvSource({
            "Nadia, 21, W, Chorna",
            "Max, 25, M, Chorniy",
            "Tom, 0, W, Koko"
    })
    void findById_ShouldReturnPatientIfExists(String name, int age, String gender, String surname) {
        Patient patient = new Patient().setName(name).setAge(age).setGender(gender).setSurname(surname);
        patientRepository.save(patient);

        Optional<Patient> optionalPatient = patientRepository.findById(patient.getId());
        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get()).isEqualTo(patient);
    }

    @Test
    void getAllWithZeroAgeAndNameContains_ShouldIgnoreCase() {
        createAndPersistListOfPatient();

        List<Patient> actualPatientList = patientRepository.getAllWithZeroAgeAndNameContains("tom");
        assertThat(actualPatientList).hasSize(1);
        assertThat(actualPatientList.getFirst().getName()).containsIgnoringCase("TOM");
        assertThat(actualPatientList.getFirst().getAge()).isEqualTo(0);
    }

    @Test
    void getAllWithZeroAgeAndNameContains_ShouldReturnEmptyListForNonExistingName() {
        createAndPersistListOfPatient();

        List<Patient> actualPatientList = patientRepository.getAllWithZeroAgeAndNameContains("noname");
        assertThat(actualPatientList).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "Nadia",
            " Max",
            "Patient Nadia",
            "Patient Max"
    })
    void getAllWithZeroAgeAndNameContains_ShouldBeEmpty(String namePart) {

        List<Patient> actualPatientList = patientRepository.getAllWithZeroAgeAndNameContains(namePart);
        assertThat(actualPatientList).hasSize(0);
    }

    private void createAndPersistListOfPatient() {
        List<Patient> patients = List.of(
                new Patient().setName("Patient Nadia").setAge(21).setGender("W").setSurname("Chorna"),
                new Patient().setName("Patient Kolia").setAge(25).setGender("M").setSurname("Chorniy"),
                new Patient().setName("Patient Max").setAge(31).setGender("M").setSurname("Bob"),
                new Patient().setName("Patient Tom").setAge(0).setGender("W").setSurname("Koko"),
                new Patient().setName("Patient Misha").setAge(26).setGender("M").setSurname("Chorniy")
        );
        patientRepository.saveAll(patients);
    }
}