package com.nure.kravchenko.student.reference.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentLoginPayload {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}
