package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.CreateStudentPayload;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;

public interface IStudentService {
    StudentDto create(CreateStudentPayload createStudentPayload);

    Student checkLogin(StudentLoginPayload loginPayload);

    Student findStudentById(Long id);

    Student save(Student student);

}
