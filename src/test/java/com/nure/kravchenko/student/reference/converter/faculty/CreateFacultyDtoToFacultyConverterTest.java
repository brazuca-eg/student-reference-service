package com.nure.kravchenko.student.reference.converter.faculty;

import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.payload.admin.CreateFacultyDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateFacultyDtoToFacultyConverterTest {

    private static final String NAME = "Name";
    private static final String SHORT_NAME = "Short Name";

    private final CreateFacultyDtoToFacultyConverter converter = new CreateFacultyDtoToFacultyConverter();

    @Test
    void convert() {
        //GIVEN
        CreateFacultyDto createFacultyDto = CreateFacultyDto.builder()
                .name(NAME)
                .shortName(SHORT_NAME)
                .build();
        //WHEN
        Faculty faculty = converter.convert(createFacultyDto);

        //THEN
        assert faculty != null;
        assertEquals(NAME, faculty.getName());
        assertEquals(SHORT_NAME, faculty.getShortName());
    }
}