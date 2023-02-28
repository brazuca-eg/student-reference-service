package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.admin.CreateTicketRequest;
import com.nure.kravchenko.student.reference.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final ConversionService conversionService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ConversionService conversionService) {
        this.ticketRepository = ticketRepository;
        this.conversionService = conversionService;
    }

    public Ticket findTicketId(Long id) {
        Optional<Ticket> optionalStudent = ticketRepository.findById(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        throw new NotFoundException("There are problems with ticket id");
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket createTicketFromAdminRequest(CreateTicketRequest createTicketRequest) {
        return conversionService.convert(createTicketRequest, Ticket.class);
    }

}
