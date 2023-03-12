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

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String shortName;

    @Column(unique = true)
    private Integer number;

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
