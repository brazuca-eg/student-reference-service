package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;

import java.util.List;

public interface IStudentService {

    StudentDto create(RegistrationDto registrationDto);

    StudentDto checkLogin(StudentLoginPayload loginPayload);

    Student findStudentById(Long id);

    StudentDto getStudentDto(Student student);

    Student save(Student student);

    StudentGroupDto getStudentGroupByStudentId(Long id);

    Student findByEmail(String email);

    List<RequestDto> getStudentRequests(Long id);

}
