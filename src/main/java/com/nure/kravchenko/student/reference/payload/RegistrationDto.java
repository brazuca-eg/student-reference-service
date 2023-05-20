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
public class RegistrationDto {
    @NotEmpty(message = "The name can't be empty")
    @Size(min = 2,  message = "Ім'я має містити якнайменш 2 символи")
    private String name;

    @NotEmpty(message = "The surname can't be empty")
    @Size(min = 2,  message = "Прізвище має містити якнайменш 2 символи")
    private String surname;

    @NotEmpty(message = "The fatherhood can't be empty")
    @Size(min = 2,  message = "Ім'я по батькове має містити якнайменш 2 символи")
    private String fatherhood;

    private char gender;

    @NotEmpty(message = "The email can't be empty")
    private String email;

    @NotEmpty(message = "The password can't be empty")
    @Size(min = 6, message = "Пароль має містити якнайменш шість символів")
    private String password;

    @NotEmpty(message = "The role can't be empty")
    private String role;
}
