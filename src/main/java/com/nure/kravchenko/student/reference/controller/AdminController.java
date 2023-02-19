package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.payload.admin.ApproveStudentRegisterPayload;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final IStudentService studentService;

    private final IRequestService requestService;

    public AdminController(IStudentService studentService, IRequestService requestService) {
        this.studentService = studentService;
        this.requestService = requestService;
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

    public StudentGroup createGroup(){
        return null;
    }

}
