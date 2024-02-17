package net.safetynet.alerts.service;

import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    private MedicalRecord medicalRecord;

    @BeforeEach
    public void setup(){
        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        medicalRecord = MedicalRecord.builder()
                .firstName("Steve")
                .lastName("Lander")
                .birthdate("2000-01-01")
                .medications(medications)
                .allergies(allergies)
                .build();
    }

    @DisplayName("JUnit test for saveMedicalRecord method")
    @Test
    public void givenMedicalRecordObject_whenSaveMedicalRecord_thenReturnMedicalRecordObject(){
        given(medicalRecordRepository.findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName()))
                .willReturn(Optional.empty());

        given(medicalRecordRepository.save(medicalRecord)).willReturn(medicalRecord);

        System.out.println(medicalRecordRepository);
        System.out.println(medicalRecordService);

        MedicalRecord savedMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);

        System.out.println(savedMedicalRecord);
        assertThat(savedMedicalRecord).isNotNull();
    }

    @DisplayName("JUnit test for saveMedicalRecord method which throws exception")
    @Test
    public void givenExistingMedicalRecord_whenSaveMedicalRecord_thenReturnNull(){
        given(medicalRecordRepository.findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName()))
                .willReturn(Optional.of(medicalRecord));

        System.out.println(medicalRecordRepository);
        System.out.println(medicalRecordService);

        assertNull(medicalRecordService.saveMedicalRecord(medicalRecord));

        verify(medicalRecordRepository, never()).save(any(MedicalRecord.class));
    }

    @DisplayName("JUnit test for getMedicalRecordByFirstNameAndLastName method")
    @Test
    public void givenMedicalRecordFirstNameAndLastName_whenGetMedicalRecordByFirstNameAndLastName_thenReturnMedicalRecordObject() {
        given(medicalRecordRepository.findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName()))
                .willReturn(Optional.of(medicalRecord));

        MedicalRecord returnedMedicalRecord = medicalRecordService.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName()).get();

        assertThat(returnedMedicalRecord).isNotNull();
    }

    @DisplayName("JUnit test for updateMedicalRecord method")
    @Test
    public void givenMedicalRecordObject_whenUpdateMedicalRecord_thenReturnUpdatedMedicalRecord(){
        given(medicalRecordRepository.save(medicalRecord)).willReturn(medicalRecord);
        medicalRecord.setBirthdate("1999-08-19");
        medicalRecord.setFirstName("Ram");

        MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);

        assertThat(updatedMedicalRecord.getBirthdate()).isEqualTo("1999-08-19");
        assertThat(updatedMedicalRecord.getFirstName()).isEqualTo("Ram");
    }

    @DisplayName("JUnit test for deleteMedicalRecord method")
    @Test
    public void givenMedicalRecordId_whenDeleteMedicalRecord_thenNothing(){
        String medicalRecordFirstName = "Steve";
        String medicalRecordLastName = "Lander";

        willDoNothing().given(medicalRecordRepository).deleteByFirstNameAndLastName(medicalRecordFirstName, medicalRecordLastName);

        medicalRecordService.deleteMedicalRecord(medicalRecordFirstName, medicalRecordLastName);

        verify(medicalRecordRepository, times(1)).deleteByFirstNameAndLastName(medicalRecordFirstName, medicalRecordLastName);
    }
}
