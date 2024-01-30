package net.safetynet.alerts.repository;

import net.safetynet.alerts.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);
    void deleteByFirstNameAndLastName(String firstName, String lastName);
}
