package com.nure.kravchenko.student.reference.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentToUniInfoDto {

    private String facultyName;

    private String specialityName;

    private String educationalProgram;

    private String groupName;

    private LocalDate groupStartYear;

    private LocalDate groupEndYear;

    private String learnForm;

    private String degreeForm;

}
