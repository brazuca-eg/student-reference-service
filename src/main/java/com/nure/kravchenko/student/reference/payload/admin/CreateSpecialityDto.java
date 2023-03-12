package com.nure.kravchenko.student.reference.payload.admin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSpecialityDto {

    private String name;

    private String shortName;

    private Integer number;

    private Long facultyId;

}
