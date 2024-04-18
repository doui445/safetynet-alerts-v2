package net.safetynet.alerts.service;

import net.safetynet.alerts.entity.MedicalRecord;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface MedicalRecordService {
    MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);

    Optional<MedicalRecord> getMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

    ResponseEntity<MedicalRecord> updateMedicalRecord(MedicalRecord medicalRecord);

    void deleteMedicalRecord(String firstName, String lastName);
}
