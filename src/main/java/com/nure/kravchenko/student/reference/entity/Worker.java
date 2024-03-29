package com.nure.kravchenko.student.reference.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"faculty", "requests"})
@Entity
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(length = 128)
    private String password;

    @Column(length = 128)
    @Length(min = 2)
    private String name;

    @Column(length = 128)
    @Length(min = 2)
    private String surname;

    @Column(length = 128)
    @Length(min = 2)
    private String fatherhood;

    @Column
    private boolean isAdmin;

    @Column
    private boolean approved;

    @Column
    private String jobTitle;

    @Column(length = 10000)
    private char gender;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "faculty_id")
    @JsonBackReference
    private Faculty faculty;

    @OneToMany(mappedBy = "worker")
    @JsonManagedReference
    private List<Request> requests;

    public void setFaculty(Faculty faculty) {
        faculty.getWorkers().add(this);
        this.faculty = faculty;
    }
}
