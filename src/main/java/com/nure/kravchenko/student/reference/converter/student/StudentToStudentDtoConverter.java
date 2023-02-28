package com.nure.kravchenko.student.reference.converter.student;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.entity.Student;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

    @Override
    public StudentDto convert(@NonNull Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .fatherhood(student.getFatherhood())
                .email(student.getEmail())
                .gender(student.getGender())
                .approved(student.isApproved())
                .build();
    }

}
