package com.nure.kravchenko.student.reference.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTicketRequest {

    @NotEmpty(message = "Serial number of the ticket can't be empty")
    @Size(min = 2, max = 2)
    private String serialNumber;

    @NotEmpty(message = "Number of the ticket can't be empty")
    @Size(min = 8, max = 12, message = "The length of ticket number can't be less than 8 symbols")
    private String number;
    
    private LocalDate startDate;

    private LocalDate endDate;
}
