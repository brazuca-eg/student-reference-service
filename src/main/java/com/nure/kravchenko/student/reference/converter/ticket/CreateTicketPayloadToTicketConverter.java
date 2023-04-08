package com.nure.kravchenko.student.reference.converter.ticket;

import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.payload.admin.CreateTicketRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class CreateTicketPayloadToTicketConverter implements Converter<CreateTicketRequest, Ticket> {

    @Override
    public Ticket convert(@NonNull CreateTicketRequest createTicketRequest) {
        return Ticket.builder()
                .serialNumber(createTicketRequest.getSerialNumber())
                .number(createTicketRequest.getNumber())
                .startDate(createTicketRequest.getStartDate())
                .endDate(createTicketRequest.getEndDate())
                .build();
    }

}
