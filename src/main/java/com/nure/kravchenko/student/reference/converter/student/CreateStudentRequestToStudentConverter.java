package com.nure.kravchenko.student.reference.converter.student;

import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.CreateStudentRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class CreateStudentRequestToStudentConverter implements Converter<CreateStudentRequest, Student> {

    @Override
    public Student convert(@NonNull CreateStudentRequest createStudentRequest) {
        return Student.builder()
                .name(createStudentRequest.getName())
                .surname(createStudentRequest.getSurname())
                .fatherhood(createStudentRequest.getFatherhood())
                .email(createStudentRequest.getEmail())
                .password(createStudentRequest.getPassword())
                .gender(createStudentRequest.getGender())
                .approved(false)
                .build();
    }

}
