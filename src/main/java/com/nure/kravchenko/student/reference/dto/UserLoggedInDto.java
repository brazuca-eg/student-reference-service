package com.nure.kravchenko.student.reference.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoggedInDto {

    private Long id;

    private String email;

    private String name;

    private String surname;

    private String fatherhood;

    private Character gender;

    private boolean approved;

    private String role;

    private String token;

}
