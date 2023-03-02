package com.nure.kravchenko.student.reference.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FacultyDto {

    private Long id;

    private String name;

    private String shortName;

}
