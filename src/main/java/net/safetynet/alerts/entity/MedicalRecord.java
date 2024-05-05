package net.safetynet.alerts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "medicalrecord")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    private String birthdate;
    @SuppressWarnings("JpaAttributeTypeInspection")
    private Blob medications;
    @SuppressWarnings("JpaAttributeTypeInspection")
    private Blob allergies;
}
