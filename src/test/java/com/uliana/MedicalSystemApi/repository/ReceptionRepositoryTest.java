package com.uliana.MedicalSystemApi.repository;

import com.uliana.MedicalSystemApi.entity.Reception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReceptionRepositoryTest {

    @Autowired
    private ReceptionRepository receptionRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @BeforeEach
    void setUp() {
        receptionRepository.deleteAll();
    }
    @ParameterizedTest
    @CsvSource({
            "Medicine A",
            " A",
            "ine A",
            "dicine A"
    })
    void findAllByMedicinesContaining_ShouldReturnReceptionsWithMatchingMedicines(String medicinePart) throws ParseException {
        createAndPersistReception();

        List<Reception> receptions = receptionRepository.findAllByMedicinesContaining(medicinePart);
        receptions.forEach(reception -> {
            assertThat(reception.getMedicines()).contains("Medicine A");
        });

    }

    @Test
    void findAllByDate_ShouldReturnReceptionsWithMatchingDate() throws ParseException {
        createAndPersistReception();

        Date targetDate = dateFormat.parse("2024-03-07");
        List<Reception> receptions = receptionRepository.findAllByData(targetDate);
        assertThat(receptions).hasSize(1);
        assertThat(receptions.getFirst().getData()).hasSameTimeAs(targetDate);
    }

    @Test
    void findAllByMedicinesContaining_ShouldReturnEmptyListForNonExistingMedicine() throws ParseException {
        createAndPersistReception();

        List<Reception> receptions = receptionRepository.findAllByMedicinesContaining("NonExistingMedicine");
        assertThat(receptions).isEmpty();
    }

    @Test
    void findById_ShouldReturnEmptyOptionalForNonExistingId() throws ParseException {
        createAndPersistReception();
        Optional<Reception> optionalReception = receptionRepository.findById(100L);
        assertThat(optionalReception).isEmpty();
    }

    private void  createAndPersistReception() throws ParseException {
        Date targetDate = dateFormat.parse("2024-03-07");
        List<Reception> receptions = List.of(
                new Reception().setData(targetDate),
                new Reception().setMedicines("Medicine A, Medicine B")
        );

         receptionRepository.saveAll(receptions);
    }
}
