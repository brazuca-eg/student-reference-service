package com.nure.kravchenko.student.reference.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentDto {

    private Long id;

    private String name;

    private String surname;

    private String fatherhood;

    private String email;

    private Character gender;

    private boolean approved;

}
