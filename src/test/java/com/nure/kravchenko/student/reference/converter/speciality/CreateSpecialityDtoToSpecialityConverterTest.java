package com.nure.kravchenko.student.reference.converter.speciality;

import com.nure.kravchenko.student.reference.entity.Speciality;
import com.nure.kravchenko.student.reference.payload.admin.CreateSpecialityDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSpecialityDtoToSpecialityConverterTest {

    private static final Integer NUMBER = 121;

    private static final String NAME = "Speciality name";

    private static final String SHORT_NAME = "PI";

    private static final String EDUCATIONAL_PROGRAM = "Educational program";

    private final CreateSpecialityDtoToSpecialityConverter converter = new CreateSpecialityDtoToSpecialityConverter();

    @Test
    void convert() {
        //GIVEN
        CreateSpecialityDto createSpecialityDto =  CreateSpecialityDto.builder()
                .number(NUMBER)
                .name(NAME)
                .shortName(SHORT_NAME)
                .educationalProgram(EDUCATIONAL_PROGRAM)
                .build();

        Speciality expected = Speciality.builder()
                .number(NUMBER)
                .name(NAME)
                .shortName(SHORT_NAME)
                .educationalProgram(EDUCATIONAL_PROGRAM)
                .build();

        //WHEN
        Speciality actual = converter.convert(createSpecialityDto);

        //THEN
        assert actual != null;
        assertEquals(expected, actual);
    }
}