package com.nure.kravchenko.student.reference.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private List<Request> requests;
}
