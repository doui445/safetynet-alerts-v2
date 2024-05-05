package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.repository.MedicalRecordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private MedicalRecordRepository medicalRecordRepository;

    @Override
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord savedMedicalRecord = medicalRecordRepository.findByFirstNameEqualsAndLastNameEquals(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if(savedMedicalRecord != null){
            System.out.println("MedicalRecord already exist with given birthdate:" + medicalRecord.getBirthdate());
            return null;
        }
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public Optional<MedicalRecord> getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        return Optional.ofNullable(medicalRecordRepository.findByFirstNameEqualsAndLastNameEquals(firstName, lastName));
    }

    @Override
    public ResponseEntity<MedicalRecord> updateMedicalRecord(MedicalRecord medicalRecord) {
        return getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName())
                .map(savedMedicalRecord -> {
                    savedMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
                    savedMedicalRecord.setMedications(medicalRecord.getMedications());
                    savedMedicalRecord.setAllergies(medicalRecord.getAllergies());

                    MedicalRecord updatedMedicalRecord = medicalRecordRepository.save(savedMedicalRecord);
                    return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordRepository.deleteByLastNameEqualsAndFirstNameEquals(firstName, lastName);
    }
}
