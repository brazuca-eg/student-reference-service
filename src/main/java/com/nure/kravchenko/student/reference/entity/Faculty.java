package com.nure.kravchenko.student.reference.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"specialities", "workers"})
@Builder
@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String shortName;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    private List<Speciality> specialities;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    private List<Worker> workers;

}
