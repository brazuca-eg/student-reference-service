package com.nure.kravchenko.student.reference.payload.admin;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveWorkerDto {

    @NotEmpty(message = "The job title can't be empty")
    private String jobTitle;

    private Long facultyId;


}
