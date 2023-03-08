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
public class CreateRequestDto {

    @NotEmpty(message = "Serial number of the ticket can't be empty")
    @Size(min = 2, max = 2)
    private String serialNumber;

    @NotEmpty(message = "Number of the ticket can't be empty")
    @Size(min = 8, max = 12, message = "The length of ticket number can't be less than 8 symbols")
    private String number;

    @NotEmpty(message = "Reason name can't be empty")
    private String reasonName;

}
