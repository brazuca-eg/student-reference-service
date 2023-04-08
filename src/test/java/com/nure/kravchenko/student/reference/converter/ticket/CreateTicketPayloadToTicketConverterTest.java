package com.nure.kravchenko.student.reference.converter.ticket;

import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.payload.admin.CreateTicketRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTicketPayloadToTicketConverterTest {


    private static final String SERIAL_NUMBER = "KH";

    private static final String NUMBER = "12345678";

    private static final LocalDate START_DATE = LocalDate.of(2019, 8, 1);

    private static final LocalDate END_DATE = LocalDate.of(2023, 6, 30);

    private final CreateTicketPayloadToTicketConverter converter = new CreateTicketPayloadToTicketConverter();

    @Test
    void convert() {
        //GIVEN
        CreateTicketRequest createTicketRequest = CreateTicketRequest.builder()
                .serialNumber(SERIAL_NUMBER)
                .number(NUMBER)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();


        Ticket expected = Ticket.builder()
                .serialNumber(SERIAL_NUMBER)
                .number(NUMBER)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();

        //WHEN
        Ticket actual = converter.convert(createTicketRequest);

        //THEN
        assert actual != null;
        assertEquals(expected, actual);
    }
}