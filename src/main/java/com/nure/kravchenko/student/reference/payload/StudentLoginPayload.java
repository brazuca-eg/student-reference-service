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
public class StudentLoginPayload {
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6, message = "Пароль має містити якнайменш шість символів")
    private String password;
}
