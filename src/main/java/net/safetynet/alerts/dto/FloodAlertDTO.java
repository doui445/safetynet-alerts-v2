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
public class FloodAlertDTO {
    List<List<Person>> personsInAddressList;
    List<PersonInfoDTO> personInfoDTOList;
}
