package com.nure.kravchenko.student.reference.service.impl;

import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.admin.CreateTicketRequest;
import com.nure.kravchenko.student.reference.repository.TicketRepository;
import com.nure.kravchenko.student.reference.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final ConversionService conversionService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, ConversionService conversionService) {
        this.ticketRepository = ticketRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Ticket findTicketId(Long id) {
        Optional<Ticket> optionalStudent = ticketRepository.findById(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        throw new NotFoundException("There are problems with ticket id");
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket createTicketFromAdminRequest(CreateTicketRequest createTicketRequest) {
        return conversionService.convert(createTicketRequest, Ticket.class);
    }

    @Override
    public boolean checkExisting(String serialNumber, String number) {
        Optional<Ticket> ticketOptional = ticketRepository.findBySerialNumberAndNumber(serialNumber, number);
        if(ticketOptional.isPresent()){
            return true;
        }
        return false;
    }

}
