package com.nure.kravchenko.student.reference.converter.student;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.entity.Status;
import com.nure.kravchenko.student.reference.entity.Student;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

    @Override
    public StudentDto convert(@NonNull Student student) {
        Status status = student.getStatus();

        boolean active = false;
        String description = StringUtils.EMPTY;

        if(Objects.nonNull(status)){
            active = status.isActive();
            description = status.getDescription();
        }

        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .fatherhood(student.getFatherhood())
                .email(student.getEmail())
                .gender(student.getGender())
                .approved(student.isApproved())
                .active(active)
                .statusDescription(description)
                .build();
    }

}
