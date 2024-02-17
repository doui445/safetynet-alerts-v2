package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.entity.FireStation;
import net.safetynet.alerts.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FireStationServiceImpl implements FireStationService {
    private FireStationRepository fireStationRepository;

    @Override
    public List<String> getPhoneNumberOfAllPersonsCoveredByStation(String station) {
        return fireStationRepository.findPhoneNumberByStation(station);
    }

    @Override
    public FireStation saveFireStation(FireStation fireStation) {
        Optional<FireStation> savedFireStation = fireStationRepository.findByStation(fireStation.getStation());
        if(savedFireStation.isPresent()){
            System.out.println("FireStation already exist with given address:" + fireStation.getAddress());
            return null;
        }
        return fireStationRepository.save(fireStation);
    }

    @Override
    public Optional<FireStation> getFireStationByStation(String station) {
        return fireStationRepository.findByStation(station);
    }

    @Override
    public FireStation updateFireStation(FireStation fireStation) {
        return fireStationRepository.save(fireStation);
    }

    @Override
    public void deleteFireStation(String station) {
        fireStationRepository.deleteByStation(station);
    }
}
