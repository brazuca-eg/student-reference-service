package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;
import com.nure.kravchenko.student.reference.payload.CreateStudentRequest;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final IStudentService studentService;

    private final IRequestService requestService;

    public StudentController(IStudentService studentService, IRequestService requestService) {
        this.studentService = studentService;
        this.requestService = requestService;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDto> register(@RequestBody CreateStudentRequest createStudentRequest) {
        StudentDto studentDto = studentService.create(createStudentRequest);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<StudentDto> login(@RequestBody @Valid StudentLoginPayload loginPayload) {
        StudentDto studentDto = studentService.checkLogin(loginPayload);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.findStudentById(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/request")
    public ResponseEntity<Request> createRequest(@PathVariable Long id,
                                                 @Valid @RequestBody CreateRequestPayload requestPayload) {
        Student student = studentService.findStudentById(id);
        Request createdRequest = requestService.createRequest(student, requestPayload);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/group")
    public ResponseEntity<StudentGroupDto> getGroupByStudent(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentGroupByStudentId(id), HttpStatus.OK);
    }

}
