package com.nure.kravchenko.student.reference.converter.faculty;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class FacultyToFacultyDtoConverter implements Converter<Faculty, FacultyDto>  {

    @Override
    public FacultyDto convert(@NonNull Faculty faculty) {
        return FacultyDto.builder()
                .id(faculty.getId())
                .name(faculty.getName())
                .shortName(faculty.getShortName())
                .build();
    }

}
