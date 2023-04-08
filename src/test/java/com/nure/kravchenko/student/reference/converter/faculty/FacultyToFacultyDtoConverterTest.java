package com.nure.kravchenko.student.reference.converter.faculty;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FacultyToFacultyDtoConverterTest {

    private static final String NAME = "Name";
    private static final String SHORT_NAME = "Short Name";

    private final FacultyToFacultyDtoConverter converter = new FacultyToFacultyDtoConverter();

    @Test
    void convert() {
        //GIVEN
        Faculty faculty = Faculty.builder()
                .name(NAME)
                .shortName(SHORT_NAME)
                .build();
        //WHEN
        FacultyDto facultyDto = converter.convert(faculty);

        //THEN
        assert facultyDto != null;
        assertEquals(NAME, facultyDto.getName());
        assertEquals(SHORT_NAME, facultyDto.getShortName());
    }
}