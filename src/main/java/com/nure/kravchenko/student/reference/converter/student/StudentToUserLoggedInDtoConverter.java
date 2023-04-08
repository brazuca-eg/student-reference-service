package com.nure.kravchenko.student.reference.converter.student;

import com.nure.kravchenko.student.reference.dto.UserLoggedInDto;
import com.nure.kravchenko.student.reference.entity.Student;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StudentToUserLoggedInDtoConverter implements Converter<Student, UserLoggedInDto> {

    @Override
    public UserLoggedInDto convert(@NonNull Student student) {
        return UserLoggedInDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .name(student.getName())
                .surname(student.getSurname())
                .fatherhood(student.getFatherhood())
                .gender(student.getGender())
                .approved(student.isApproved())
                .build();
    }

}
