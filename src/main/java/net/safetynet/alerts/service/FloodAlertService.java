package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.dto.FloodAlertDTO;
import net.safetynet.alerts.dto.PersonInfoDTO;
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
public class FloodAlertService {
    private PersonRepository personRepository;
    private FireStationRepository fireStationRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public List<FloodAlertDTO> getAddressAndPeopleByFloodStations(List<String> stations) {
        List<FloodAlertDTO> floodAlertDTOList = new ArrayList<>();
        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
        List<String> addressList = new ArrayList<>();
        List<List<Person>> personsInAddressList = new ArrayList<>();

        for (String station : stations) {
            Optional<FireStation> fireStation = Optional.ofNullable(fireStationRepository.findByStation(station).getFirst());
            fireStation.ifPresent(value -> addressList.add(value.getAddress()));
        } for (String address : addressList) {
            List<Person> personList = personRepository.findAllByAddress(address);
            for (Person person : personList) {
                PersonInfoDTO personInfo = new PersonInfoDTO();
                MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameEqualsAndLastNameEquals(person.getFirstName(), person.getLastName());
                if (medicalRecord != null) {
                    LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate());
                    personInfo.setLastName(personInfo.getLastName());
                    personInfo.setAddress(person.getAddress());
                    personInfo.setAge(Period.between(birthdate, LocalDate.now()).getYears());
                    personInfo.setEmail(person.getEmail());
                    personInfo.setMedications(medicalRecord.getMedications());
                    personInfo.setAllergies(medicalRecord.getAllergies());
                }
                personInfoDTOList.add(personInfo);
            }
            personsInAddressList.add(personList);
        }
        floodAlertDTOList.add(new FloodAlertDTO(personsInAddressList, personInfoDTOList));
        return floodAlertDTOList;
    }
}
