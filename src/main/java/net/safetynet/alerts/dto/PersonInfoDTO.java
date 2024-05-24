package net.safetynet.alerts.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "medications", "allergies" })
public class PersonInfoDTO {
    private String lastName;
    private String address;
    private Integer age;
    private String email;
    private Blob medications;
    private Blob allergies;
}
