package net.safetynet.alerts.controller;

import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.service.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.h2.mvstore.type.ObjectDataType.serialize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {
    @Mock
    private MedicalRecordServiceImpl medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    private MedicalRecord medicalRecord;

    @BeforeEach
    public void setup() {
        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        try {
            medicalRecord = MedicalRecord.builder()
                    .firstName("Steve")
                    .lastName("Lander")
                    .birthdate("2000-01-01")
                    .medications(new SerialBlob(serialize(medications)))
                    .allergies(new SerialBlob(serialize(allergies)))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for createMedicalRecord method")
    @Test
    public void givenMedicalRecordObject_whenCreateMedicalRecord_thenReturnMedicalRecordObject() {
        given(medicalRecordService.saveMedicalRecord(medicalRecord))
                .willReturn(medicalRecord);
        MedicalRecord savedMedicalRecord = medicalRecordController.createMedicalRecord(medicalRecord);
        assertThat(savedMedicalRecord).isEqualTo(medicalRecord);
    }

    @DisplayName("JUnit test for updateMedicalRecord method")
    @Test
    public void givenMedicalRecordObject_whenUpdateMedicalRecord_thenReturnResponseEntityOfMedicalRecord() {
        given(medicalRecordService.updateMedicalRecord(medicalRecord))
                .willReturn(new ResponseEntity<>(medicalRecord, HttpStatus.OK));
        ResponseEntity<MedicalRecord> updatedMedicalRecord = medicalRecordController.updateMedicalRecord(medicalRecord);
        assertThat(updatedMedicalRecord.getBody()).isEqualTo(medicalRecord);
        assertThat(updatedMedicalRecord.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("JUnit test for deleteMedicalRecord method")
    @Test
    public void givenMedicalRecordObject_whenDeleteMedicalRecord_thenReturnResponseEntityOK() {
        doNothing().when(medicalRecordService).deleteMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
        ResponseEntity<String> isDeleted = medicalRecordController.deleteMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(isDeleted.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
