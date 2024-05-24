package net.safetynet.alerts.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.safetynet.alerts.entity.MedicalRecord;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "medicalRecord" })
public class FireAlertDTO {
    private String lastName;
    private String phone;
    private Integer age;
    private MedicalRecord medicalRecord;
    private Long stationNumber;
}
