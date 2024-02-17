package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.service.MedicalRecordServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/medicalrecord")
public class MedicalRecordController {
    private MedicalRecordServiceImpl medicalRecordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName())
                .map(savedMedicalRecord -> {
                    savedMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
                    savedMedicalRecord.setMedications(medicalRecord.getMedications());
                    savedMedicalRecord.setAllergies(medicalRecord.getAllergies());

                    MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(savedMedicalRecord);
                    return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord of " + firstName + lastName + " deleted successfully!");
    }
}
