package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.payload.admin.CreateTicketRequest;

public interface TicketService {

    Ticket findTicketId(Long id);

    Ticket save(Ticket ticket);

    Ticket createTicketFromAdminRequest(CreateTicketRequest createTicketRequest);

    boolean checkExisting(String serialNumber, String number);

}
