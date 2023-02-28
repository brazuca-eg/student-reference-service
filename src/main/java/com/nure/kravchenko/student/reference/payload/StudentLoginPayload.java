package com.nure.kravchenko.student.reference.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentLoginPayload {
    @NotEmpty(message = "The email can't be empty")
    private String email;

    @NotEmpty(message = "The password can't be empty")
    //@Min(value = 4, message = "The password must contain at least more than 6 characters")
    private String password;
}
