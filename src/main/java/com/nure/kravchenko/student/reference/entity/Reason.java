package com.nure.kravchenko.student.reference.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"requests"})
@Entity
public class Reason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 120)
    private String name;

    @Column(unique = true, length = 500)
    private String description;

    @OneToMany(mappedBy = "reason")
    @JsonManagedReference
    private List<Request> requests;
}
