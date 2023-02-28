package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.payload.CreateStudentRequest;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;

public interface IStudentService {
    StudentDto create(CreateStudentRequest createStudentRequest);

    StudentDto checkLogin(StudentLoginPayload loginPayload);

    Student findStudentById(Long id);

    Student save(Student student);

    StudentGroupDto getStudentGroupByStudentId(Long id);

}
