package com.nure.kravchenko.student.reference.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"studentGroups", "faculty"})
@Builder
@Entity
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String shortName;

    @Column
    private Integer number;

    @Column(unique = true, length = 128)
    private String educationalProgram;

    @OneToMany(mappedBy = "speciality")
    @JsonManagedReference
    private List<StudentGroup> studentGroups;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "faculty_id")
    @JsonBackReference
    private Faculty faculty;

    public void setFaculty(Faculty faculty) {
        faculty.getSpecialities().add(this);
        this.faculty = faculty;
    }

}
