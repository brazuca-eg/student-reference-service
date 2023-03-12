package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.*;
import com.nure.kravchenko.student.reference.entity.*;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.admin.*;
import com.nure.kravchenko.student.reference.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final IStudentService studentService;

    private final IRequestService requestService;

    private final WorkerService workerService;

    private final FacultyService facultyService;

    private final ReasonService reasonService;

    private final StudentGroupService studentGroupService;

    private final TicketService ticketService;

    private final SpecialityService specialityService;

    public AdminController(IStudentService studentService, IRequestService requestService, WorkerService workerService, FacultyService facultyService, ReasonService reasonService,
                           StudentGroupService studentGroupService, TicketService ticketService, SpecialityService specialityService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.workerService = workerService;
        this.facultyService = facultyService;
        this.reasonService = reasonService;
        this.studentGroupService = studentGroupService;
        this.ticketService = ticketService;
        this.specialityService = specialityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerDto> getAdminById(@PathVariable Long id) {
        WorkerDto workerDto = workerService.getWorkerDtoById(id);
        if (workerDto.isAdmin()) {
            return new ResponseEntity<>(workerDto, HttpStatus.OK);
        }
        throw new NotFoundException("This is not admin");
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentDto(studentService.findStudentById(id)), HttpStatus.OK);
    }

    @GetMapping("/approve/workers")
    public ResponseEntity<List<WorkerDto>> getWaitingApproveWorkers() {
        return new ResponseEntity<>(workerService.getWaitingApproveWorkers(), HttpStatus.OK);
    }

    @GetMapping("/approve/students")
    public ResponseEntity<List<StudentDto>> getWaitingApproveStudents() {
        return new ResponseEntity<>(studentService.getWaitingApproveStudents(), HttpStatus.OK);
    }

    @GetMapping("/faculties")
    public ResponseEntity<List<FacultyDto>> getAllFaculties() {
        return new ResponseEntity<>(facultyService.getAllFaculties(), HttpStatus.OK);
    }

    @GetMapping("/specialities")
    public ResponseEntity<List<SpecialityDto>> getAllSpecialities() {
        return new ResponseEntity<>(specialityService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/students/{studentId}/approve")
    public ResponseEntity<StudentDto> approveStudentRegistration(
            @PathVariable Long studentId,
            @RequestBody @Valid ApproveStudentRegisterDto approveStudentRegisterDto) {
        Student student = studentService.findStudentById(studentId);
        StudentGroup studentGroup = studentGroupService.findGroupByName(approveStudentRegisterDto.getGroupName());

        String serialNumber = approveStudentRegisterDto.getSerialNumber();
        String number = approveStudentRegisterDto.getNumber();
        boolean existing = ticketService.checkExisting(serialNumber, number);
        if (existing) {
            throw new RuntimeException("The ticket already exists");
        } else {
            Ticket ticket = Ticket.builder()
                    .number(number)
                    .serialNumber(serialNumber)
                    .startDate(approveStudentRegisterDto.getStartDate())
                    .endDate(approveStudentRegisterDto.getEndDate())
                    .build();
            return new ResponseEntity<>(studentService.approveStudentRegistration(student, studentGroup, ticket),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/workers/{workerId}/approve")
    public ResponseEntity<WorkerDto> approveWorkerRegistration(@PathVariable Long workerId,
                                                               @RequestBody @Valid ApproveWorkerDto approveWorkerDto) {

        Faculty faculty = facultyService.findById(approveWorkerDto.getFacultyId());
        WorkerDto workerDto = workerService.approveWorker(workerId, approveWorkerDto, faculty);

        return new ResponseEntity<>(workerDto, HttpStatus.CREATED);
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

    @PostMapping("/faculty")
    public ResponseEntity<FacultyDto> createFaculty(@RequestBody @Valid CreateFacultyDto createFacultyDto) {
        return new ResponseEntity<>(facultyService.createFaculty(createFacultyDto), HttpStatus.CREATED);
    }

    @PostMapping("/speciality")
    public ResponseEntity<SpecialityDto> createSpeciality(@RequestBody @Valid CreateSpecialityDto createSpecialityDto) {
        Faculty faculty = facultyService.findById(createSpecialityDto.getFacultyId());
        return new ResponseEntity<>(specialityService.createSpeciality(createSpecialityDto, faculty), HttpStatus.CREATED);
    }

    @PostMapping("/group")
    public ResponseEntity<StudentGroupDto> createGroup(@RequestBody @Valid CreateGroupDto createGroupDto) {
        Speciality speciality = specialityService.findById(createGroupDto.getSpecialityId());
        return new ResponseEntity<>(studentGroupService.createGroup(createGroupDto, speciality), HttpStatus.CREATED);
    }

}
