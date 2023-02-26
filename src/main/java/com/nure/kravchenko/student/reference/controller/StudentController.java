package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;
import com.nure.kravchenko.student.reference.payload.CreateStudentPayload;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/student")
//@Validated
public class StudentController {
    private final IStudentService studentService;

    private final IRequestService requestService;

    public StudentController(IStudentService studentService, IRequestService requestService) {
        this.studentService = studentService;
        this.requestService = requestService;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDto> register(@RequestBody CreateStudentPayload createStudentPayload) {
        StudentDto studentDto = studentService.create(createStudentPayload);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Student> login(@RequestBody @Valid StudentLoginPayload loginPayload) {
        //validate student
        Student studentDto = studentService.checkLogin(loginPayload);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/request")
    public ResponseEntity<Request> createRequest(@PathVariable Long id,
                                                 @Valid @RequestBody CreateRequestPayload requestPayload) {
        Student student = studentService.findStudentById(id);
        Request createdRequest = requestService.createRequest(student, requestPayload);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/{id}/group")
    public ResponseEntity<StudentGroupDto> getGroupByStudent(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);

        StudentGroup studentGroup = student.getStudentGroup();
        if (Objects.nonNull(studentGroup)) {
            StudentGroupDto studentDto = StudentGroupDto.builder()
                    .name(studentGroup.getName())
                    .startYear(studentGroup.getStartYear())
                    .endYear(studentGroup.getEndYear())
                    .learnForm(studentGroup.getLearnForm())
                    .build();
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        }
        throw new NotFoundException("Student not in a group ex");
    }

}
