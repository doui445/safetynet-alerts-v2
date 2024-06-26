package net.safetynet.alerts.repository;

import net.safetynet.alerts.entity.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {
    FireStation findByAddressEquals(String address);

    @Query(value = "SELECT a.phone FROM Person AS a INNER JOIN FireStation AS b ON a.address = b.address WHERE b.station = ?1", nativeQuery = true)
    List<String> findPhoneNumberByStation(String station);

    @Query("select a from FireStation a where a.station = ?1")
    List<FireStation> findByStation(String station);

    void deleteByStationEquals(String station);
}
