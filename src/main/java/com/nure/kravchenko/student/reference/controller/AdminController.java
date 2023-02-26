package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.admin.ApproveStudentRegisterPayload;
import com.nure.kravchenko.student.reference.payload.admin.CreateReasonPayload;
import com.nure.kravchenko.student.reference.payload.admin.CreateTicketPayload;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import com.nure.kravchenko.student.reference.service.ReasonService;
import com.nure.kravchenko.student.reference.service.StudentGroupService;
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

    private final StudentGroupService studentGroupService;

    public AdminController(IStudentService studentService, IRequestService requestService, ReasonService reasonService, StudentGroupService studentGroupService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.reasonService = reasonService;
        this.studentGroupService = studentGroupService;
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
            student.setTicket(ticket);
            studentService.save(student);

            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        }
        throw new NotFoundException("No such student for ticket creation");
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
