package com.nure.kravchenko.student.reference.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentGroupDto {

    private Long id;

    private String name;

    private LocalDate startYear;

    private LocalDate endYear;

    private String learnForm;

    private String degreeForm;

}
