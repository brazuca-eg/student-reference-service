package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;
import com.nure.kravchenko.student.reference.payload.CreateStudentPayload;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final IStudentService studentService;

    private final IRequestService requestService;

    public StudentController(IStudentService studentService, IRequestService requestService) {
        this.studentService = studentService;
        this.requestService = requestService;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDto> register(@RequestBody CreateStudentPayload createStudentPayload) {
        //validate student
        StudentDto studentDto = studentService.create(createStudentPayload);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<StudentDto> login(@RequestBody StudentLoginPayload loginPayload) {
        //validate student
        StudentDto studentDto = studentService.checkLogin(loginPayload);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/request")
    public ResponseEntity<Request> createRequest(@PathVariable Long id, @RequestBody CreateRequestPayload requestPayload) {
        Student student = studentService.findStudentById(id);
        Request createdRequest = requestService.createRequest(student, requestPayload);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

}
