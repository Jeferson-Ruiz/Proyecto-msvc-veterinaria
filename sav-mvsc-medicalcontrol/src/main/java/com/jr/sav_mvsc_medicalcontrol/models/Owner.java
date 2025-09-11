package com.jr.sav_mvsc_medicalcontrol.models;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long idOwner;

    @Column(name = "owner_name", nullable = false)
    private String name;

    @Column(name = "owner_last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "owner_documt_type", nullable = false, length = 15)
    private String documentType;

    @Column(name = "owner_documt_number", nullable = false)
    private String documentNumber;

    @Column(name = "owner_email", nullable = false, length = 50)
    private String email;

    @Column(name = "owner_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "owner_data_of_recording", nullable = false)
    private LocalDate dateOfRecording;

    @Column(name = "owner_active")
    private Boolean active;

    @OneToMany(mappedBy = "owner")
    private List<Pet> pets;

}
