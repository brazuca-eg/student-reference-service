package com.nure.kravchenko.student.reference.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"student"})
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String serialNumber;
    @Column(unique = true)
    private String number;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    public void setStudent(Student student) {
        student.setTicket(this);
        this.student = student;
    }

}
