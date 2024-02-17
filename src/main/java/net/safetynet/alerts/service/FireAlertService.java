package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.dto.FireAlertDTO;
import net.safetynet.alerts.entity.FireStation;
import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.repository.FireStationRepository;
import net.safetynet.alerts.repository.MedicalRecordRepository;
import net.safetynet.alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FireAlertService {
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private FireStationRepository fireStationRepository;

    public List<FireAlertDTO> getFireStationAndPersonsByAddress(String address) {
        List<Person> people = personRepository.findAllByAddress(address);
        FireStation fireStation = fireStationRepository.findByAddress(address);
        List<FireAlertDTO> fireAlertDTOList = new ArrayList<>();
        for (Person person : people) {
            Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            if (optionalMedicalRecord.isPresent()) {
                MedicalRecord medicalRecord = optionalMedicalRecord.get();
                LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate());
                FireAlertDTO fireAlertDTO = new FireAlertDTO();
                fireAlertDTO.setLastName(person.getLastName());
                fireAlertDTO.setPhone(person.getPhone());
                fireAlertDTO.setAge(Period.between(birthdate, LocalDate.now()).getYears());
                fireAlertDTO.setMedicalRecord(medicalRecord);
                fireAlertDTO.setStationNumber(fireStation.getId());
                fireAlertDTOList.add(fireAlertDTO);
            }
        }
        return fireAlertDTOList;
    }
}
