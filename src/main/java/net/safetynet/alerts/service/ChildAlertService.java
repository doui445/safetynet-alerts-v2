package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.dto.ChildAlertDTO;
import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.repository.MedicalRecordRepository;
import net.safetynet.alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class ChildAlertService {
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public List<ChildAlertDTO> getAllChildLivingAtAddress(String address) {
        List<Person> people = personRepository.findAllByAddress(address);
        List<ChildAlertDTO> childAlertDTOList = new ArrayList<>();
        for (Person person : people) {
            MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameEqualsAndLastNameEquals(person.getFirstName(), person.getLastName());
            if (medicalRecord != null) {
                LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate());
                int age = Period.between(birthdate, LocalDate.now()).getYears();
                if (age < 18) {
                    ChildAlertDTO childAlert = new ChildAlertDTO();
                    childAlert.setFirstName(person.getFirstName());
                    childAlert.setLastName(person.getLastName());
                    childAlert.setAge(age);
                    List<Person> familyMembers = new ArrayList<>();
                    for (Person familyMember : people) {
                        if (Objects.equals(familyMember.getLastName(), person.getLastName())) {
                            familyMembers.add(familyMember);
                        }
                    }
                    childAlert.setFamilyMembers(familyMembers);
                    childAlertDTOList.add(childAlert);
                }
            }
        }
        return childAlertDTOList;
    }
}
