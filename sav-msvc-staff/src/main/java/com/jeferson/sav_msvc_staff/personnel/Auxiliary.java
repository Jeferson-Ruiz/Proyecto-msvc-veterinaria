package com.jeferson.sav_msvc_staff.personnel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Auxiliaries")
public class Auxiliary  extends Person{
    private String workShift;
    private String area;
    private List<String> certifications;
}