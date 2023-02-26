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
@ToString(exclude = {"ticket", "studentGroup", "requests"})
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(length = 20)
    private String password;
    @Column(length = 128)
    private String name;
    @Column(length = 128)
    private String surname;
    @Column(length = 128)
    private String fatherhood;
    @Column(length = 1)
    private Character gender;
    @Column
    private boolean approved;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_group_id")
    @JsonBackReference
    private StudentGroup studentGroup;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<Request> requests;

    public void setTicket(Ticket ticket) {
        ticket.setStudent(this);
        this.ticket = ticket;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        studentGroup.getStudents().add(this);
        this.studentGroup = studentGroup;
    }

}
