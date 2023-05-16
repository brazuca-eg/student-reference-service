package com.nure.kravchenko.student.reference.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStudentStatusDto {

    @NotEmpty(message = "Опис не має бути порожнім")
    private String description;

    private LocalDate endDate;

    private boolean status;

}
