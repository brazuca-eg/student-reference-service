package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.admin.ApproveStudentRegisterPayload;
import com.nure.kravchenko.student.reference.payload.admin.CreateReasonPayload;
import com.nure.kravchenko.student.reference.payload.admin.CreateTicketPayload;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import com.nure.kravchenko.student.reference.service.ReasonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final IStudentService studentService;

    private final IRequestService requestService;

    private final ReasonService reasonService;

    public AdminController(IStudentService studentService, IRequestService requestService, ReasonService reasonService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.reasonService = reasonService;
    }

    @PostMapping("/student/{studentId}/approve")
    public String approveStudentRegistration(@PathVariable Long studentId,
                                             ApproveStudentRegisterPayload approveStudentRegisterPayload) {
//        Student student = studentService.findStudentById(studentId);
//        if(Objects.nonNull(student)){
//
//        }
        return "";
    }

    @PostMapping("/students/{studentId}/ticket")
    public ResponseEntity<Ticket> addTicket(@PathVariable Long studentId,
                                            @Valid @RequestBody CreateTicketPayload ticketPayload) throws Exception {
        Student student = studentService.findStudentById(studentId);
        if (Objects.nonNull(student)) {
            if (Objects.nonNull(student.getTicket())) {
                throw new Exception("The student has already have a ticket");
            }
            Ticket ticket = Ticket.builder()
                    .serialNumber(ticketPayload.getSerialNumber())
                    .number(ticketPayload.getNumber())
                    .startDate(ticketPayload.getStartDate())
                    .endDate(ticketPayload.getEndDate())
                    .build();

            // TODO: 23.02.2023   replace backward
            student.setTicket(ticket);
            studentService.save(student);

            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        }
        throw new NotFoundException("No such student for ticket creation");
    }

    @PostMapping("/reason")
    public ResponseEntity<Reason> createReason(@Valid @RequestBody CreateReasonPayload reasonPayload) {
        Reason reason = Reason.builder()
                .name(reasonPayload.getName())
                .description(reasonPayload.getDescription())
                .build();
        reasonService.save(reason);

        return new ResponseEntity<>(reason, HttpStatus.CREATED);
    }

}
