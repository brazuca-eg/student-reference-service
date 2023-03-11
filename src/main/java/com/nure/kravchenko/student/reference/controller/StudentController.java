package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.ReasonDto;
import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.CreateRequestDto;
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
    public ResponseEntity<List<RequestDto>> getRequestForStudent(@PathVariable Long id, @RequestParam String filter) {
        return new ResponseEntity<>(studentService.getStudentRequests(id,filter), HttpStatus.CREATED);
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
