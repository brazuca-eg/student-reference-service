package com.nure.kravchenko.student.reference.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReasonPayload {

    @NotEmpty(message = "The name can't be empty")
    private String name;

    @NotEmpty(message = "The description can't be empty")
    private String description;

}
