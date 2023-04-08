package com.nure.kravchenko.student.reference.converter.faculty;

import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.payload.admin.CreateFacultyDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class CreateFacultyDtoToFacultyConverter implements Converter<CreateFacultyDto, Faculty> {

    @Override
    public Faculty convert(@NonNull CreateFacultyDto createFacultyDto) {
        return Faculty.builder()
                .name(createFacultyDto.getName())
                .shortName(createFacultyDto.getShortName())
                .build();
    }

}
