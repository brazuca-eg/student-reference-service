package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.payload.admin.ApproveStudentRegisterPayload;
import com.nure.kravchenko.student.reference.payload.admin.CreateReasonPayload;
import com.nure.kravchenko.student.reference.payload.admin.CreateTicketRequest;
import com.nure.kravchenko.student.reference.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final IStudentService studentService;

    private final IRequestService requestService;

    private final ReasonService reasonService;

    private final StudentGroupService studentGroupService;

    private final TicketService ticketService;

    public AdminController(IStudentService studentService, IRequestService requestService, ReasonService reasonService,
                           StudentGroupService studentGroupService, TicketService ticketService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.reasonService = reasonService;
        this.studentGroupService = studentGroupService;
        this.ticketService = ticketService;
    }

    @PostMapping("/students/{studentId}/approve")
    public Student approveStudentRegistration(
            @PathVariable Long studentId,
            @RequestBody @Valid ApproveStudentRegisterPayload approveStudentRegisterPayload) {
        Student student = studentService.findStudentById(studentId);
        StudentGroup studentGroup = studentGroupService.findGroupByName(approveStudentRegisterPayload.getGroupName());

        student.setStudentGroup(studentGroup);

        return student;
    }

    @PostMapping("/students/{studentId}/ticket")
    public ResponseEntity<Ticket> addTicket(@PathVariable Long studentId,
                                            @Valid @RequestBody CreateTicketRequest ticketRequest) throws Exception {
        Student student = studentService.findStudentById(studentId);
        if (Objects.nonNull(student.getTicket())) {
            throw new Exception("The student has already have a ticket");
        }
        Ticket newTicket = ticketService.createTicketFromAdminRequest(ticketRequest);
        student.setTicket(newTicket);
        studentService.save(student);

        // TODO: 28.02.2023 "id": null in response for ticket
        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    @PostMapping("/reason")
    public ResponseEntity<Reason> createReason(@RequestBody @Valid CreateReasonPayload reasonPayload) {
        Reason reason = Reason.builder()
                .name(reasonPayload.getName())
                .description(reasonPayload.getDescription())
                .build();
        reasonService.save(reason);

        return new ResponseEntity<>(reason, HttpStatus.CREATED);
    }

}
