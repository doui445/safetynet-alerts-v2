package net.safetynet.alerts.repository;

import net.safetynet.alerts.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    //@Query("SELECT a FROM MedicalRecord a WHERE a.firstName = ?1 and a.lastName = ?2")
    MedicalRecord findByFirstNameEqualsAndLastNameEquals(String firstName, String lastName);

    void deleteByLastNameEqualsAndFirstNameEquals(String firstName, String lastName);
}
