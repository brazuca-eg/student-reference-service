package com.nure.kravchenko.student.reference.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthenticationRequestDTO {
    @NotEmpty(message = "The email can't be empty")
    private String email;

    @NotEmpty(message = "The password can't be empty")
    private String password;

}