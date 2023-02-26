package com.nure.kravchenko.student.reference.payload;

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
public class CreateStudentPayload {
    @NotEmpty(message = "The name can't be empty")
    private String name;

    @NotEmpty(message = "The surname can't be empty")
    private String surname;

    @NotEmpty(message = "The fatherhood can't be empty")
    private String fatherhood;

    @NotEmpty(message = "The gender can't be empty")
    @Size(message = "The gender contain only one character")
    private Character gender;

    @NotEmpty(message = "The email can't be empty")
    private String email;

    @NotEmpty(message = "The password can't be empty")
    private String password;
}
