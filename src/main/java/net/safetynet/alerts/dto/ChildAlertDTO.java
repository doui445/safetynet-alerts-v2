package net.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.safetynet.alerts.entity.Person;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Person> familyMembers;
}
