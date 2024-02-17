package net.safetynet.alerts.repository;

import net.safetynet.alerts.entity.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MedicalRecordRepositoryTest {
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    private MedicalRecord medicalRecord;

    @BeforeEach
    public void setUp() {
        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        medicalRecord = MedicalRecord.builder()
                .firstName("John")
                .lastName("Boyd")
                .birthdate("2000-01-01")
                .medications(medications)
                .allergies(allergies)
                .build();
    }

    @DisplayName("JUnit test for save medical record operation")
    @Test
    public void givenMedicalRecordObject_whenSave_thenReturnSavedMedicalRecord() {
        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("John")
                .lastName("Boyd")
                .birthdate("2000-01-01")
                .medications(medications)
                .allergies(allergies)
                .build();
        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
        assertThat(savedMedicalRecord).isNotNull();
        assertThat(savedMedicalRecord.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all medical records operation")
    @Test
    public void givenMedicalRecordsList_whenFindAll_thenMedicalRecordsList() {
        List<MedicalRecord> oldMedicalRecordList = medicalRecordRepository.findAll();
        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        MedicalRecord medicalRecord1 = MedicalRecord.builder()
                .firstName("Peter")
                .lastName("Duncan")
                .birthdate("1999-08-19")
                .medications(medications)
                .allergies(allergies)
                .build();
        medicalRecordRepository.save(medicalRecord);
        medicalRecordRepository.save(medicalRecord1);

        List<MedicalRecord> newMedicalRecordList = medicalRecordRepository.findAll();
        newMedicalRecordList.removeAll(oldMedicalRecordList);

        assertThat(newMedicalRecordList).isNotNull();
        assertThat(newMedicalRecordList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for get medical record by id operation")
    @Test
    public void givenMedicalRecordObject_whenFindById_thenReturnMedicalRecordObject() {
        medicalRecordRepository.save(medicalRecord);
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findById(medicalRecord.getId());
        assertThat(optionalMedicalRecord.isPresent()).isTrue();
        MedicalRecord medicalRecordDB = optionalMedicalRecord.get();
        assertThat(medicalRecordDB).isNotNull();
    }

    @DisplayName("JUnit test for update medical record operation")
    @Test
    public void givenMedicalRecordObject_whenUpdateMedicalRecord_thenReturnUpdatedMedicalRecord() {
        medicalRecordRepository.save(medicalRecord);

        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findById(medicalRecord.getId());
        assertThat(optionalMedicalRecord.isPresent()).isTrue();

        MedicalRecord savedMedicalRecord = optionalMedicalRecord.get();
        savedMedicalRecord.setBirthdate("1999-08-19");
        savedMedicalRecord.setFirstName("Ram");
        MedicalRecord updatedMedicalRecord =  medicalRecordRepository.save(savedMedicalRecord);


        assertThat(updatedMedicalRecord.getBirthdate()).isEqualTo("1999-08-19");
        assertThat(updatedMedicalRecord.getFirstName()).isEqualTo("Ram");
    }

    @DisplayName("JUnit test for delete medical record operation")
    @Test
    public void givenMedicalRecordObject_whenDelete_thenRemoveMedicalRecord() {
        medicalRecordRepository.save(medicalRecord);

        medicalRecordRepository.deleteById(medicalRecord.getId());
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findById(medicalRecord.getId());

        assertThat(optionalMedicalRecord).isEmpty();
    }
}
