package com.nure.kravchenko.student.reference.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"students", "speciality"})
@Entity(name = "student_group")
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20)
    private String name;

    @Column
    private LocalDate startYear;
    @Column
    private LocalDate endYear;

    @Column
    private String learnForm;

    @Column
    private String degreeForm;

    @OneToMany(mappedBy = "studentGroup")
    @JsonManagedReference
    private List<Student> students;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "speciality_id")
    @JsonBackReference
    private Speciality speciality;

}
