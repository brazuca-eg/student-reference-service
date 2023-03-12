package com.nure.kravchenko.student.reference.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFacultyDto {

    @NotEmpty(message = "The name of the faculty can't be empty")
    private String name;

    @NotEmpty(message = "The short name  of the faculty can't be empty")
    @Size(min = 2, max = 2, message = "The short name of the faculty must be with 2 chars")
    private String shortName;

}
