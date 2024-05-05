package net.safetynet.alerts.dto;

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
public class PersonInfoDTO {
    private String lastName;
    private String address;
    private Integer age;
    private String email;
    private Blob medications;
    private Blob allergies;
}
