package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.dto.PersonInfoDTO;
import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.repository.MedicalRecordRepository;
import net.safetynet.alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PersonInfoService {
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public PersonInfoDTO getPersonInfoByFirstNameAndLastName(String firstName, String lastName) {
        Optional<Person> optionalPerson = Optional.ofNullable(personRepository.findByFirstNameEqualsAndLastNameEquals(firstName, lastName));
        Optional<MedicalRecord> optionalMedicalRecord = Optional.ofNullable(medicalRecordRepository.findByFirstNameEqualsAndLastNameEquals(firstName, lastName));
        PersonInfoDTO personInfo = new PersonInfoDTO();
        if (optionalPerson.isPresent() && optionalMedicalRecord.isPresent()) {
            Person person = optionalPerson.get();
            MedicalRecord medicalRecord = optionalMedicalRecord.get();
            LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate());
            personInfo.setLastName(lastName);
            personInfo.setAddress(person.getAddress());
            personInfo.setAge(Period.between(birthdate, LocalDate.now()).getYears());
            personInfo.setEmail(person.getEmail());
            personInfo.setMedications(medicalRecord.getMedications());
            personInfo.setAllergies(medicalRecord.getAllergies());
        }
        return personInfo;
    }
}
