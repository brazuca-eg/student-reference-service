package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;

public interface IStudentService {
    StudentDto create(RegistrationDto registrationDto);

    StudentDto checkLogin(StudentLoginPayload loginPayload);

    Student findStudentById(Long id);

    Student save(Student student);

    StudentGroupDto getStudentGroupByStudentId(Long id);

}
