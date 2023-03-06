package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.ReasonDto;
import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import com.nure.kravchenko.student.reference.service.ReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final IStudentService studentService;

    private final IRequestService requestService;

    private final ReasonService reasonService;

    @Autowired
    public StudentController(IStudentService studentService, IRequestService requestService,
                             ReasonService reasonService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.reasonService = reasonService;
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

    @GetMapping("/reasons")
    public ResponseEntity<List<ReasonDto>> getAllRequestReasonsForStudent() {
        return new ResponseEntity<>(reasonService.getAllReasons(), HttpStatus.OK);
    }

}
