package com.nure.kravchenko.student.reference.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"studentGroup"})
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String fatherhood;
    @Column
    private Character gender;
    @Column
    private Boolean approved = false;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_group_id")
    private StudentGroup studentGroup;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<Request> requests;

    @JsonBackReference
    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
}
