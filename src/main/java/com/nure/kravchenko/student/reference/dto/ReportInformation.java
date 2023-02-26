package com.nure.kravchenko.student.reference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportInformation {

    private String fullName;

    private String gender;

    private String studentGender;

    private String courseNumber;

    private String learnForm;

    private String faculty;

    private String degreeForm;

    private String startDate;

    private String endDate;

    private String reason;

    private String reportDate;

}
