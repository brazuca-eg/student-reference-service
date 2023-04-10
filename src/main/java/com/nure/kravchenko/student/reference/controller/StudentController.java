package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.*;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.app.RequestType;
import com.nure.kravchenko.student.reference.payload.CreateRequestDto;
import com.nure.kravchenko.student.reference.service.ReasonService;
import com.nure.kravchenko.student.reference.service.RequestService;
import com.nure.kravchenko.student.reference.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final RequestService requestService;

    private final ReasonService reasonService;

    @Autowired
    public StudentController(StudentService studentService, RequestService requestService,
                             ReasonService reasonService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.reasonService = reasonService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        return new ResponseEntity<>(studentService.getStudentDto(student), HttpStatus.OK);
    }

    @PostMapping("/{id}/requests")
    public ResponseEntity<RequestDto> createRequest(@PathVariable Long id,
                                                    @Valid @RequestBody CreateRequestDto createRequestDto) {
        Student student = studentService.findStudentById(id);
        return new ResponseEntity<>(requestService.createRequest(student, createRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<List<RequestDto>> getRequestForStudent(@PathVariable Long id, @RequestParam String filter,
                                                                 @RequestParam RequestType requestType) {
        return new ResponseEntity<>(studentService.getStudentRequests(id,requestType, filter), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/group")
    public ResponseEntity<StudentGroupDto> getGroupByStudent(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentGroupByStudentId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/uniInfo")
    public ResponseEntity<StudentToUniInfoDto> getUniInfoByStudent(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        return new ResponseEntity<>(studentService.getStudentToUniInfo(student), HttpStatus.OK);
    }

    @GetMapping("/reasons")
    public ResponseEntity<List<ReasonDto>> getAllRequestReasonsForStudent() {
        return new ResponseEntity<>(reasonService.getAllReasons(), HttpStatus.OK);
    }

}
