package com.nure.kravchenko.student.reference.converter.speciality;

import com.nure.kravchenko.student.reference.dto.SpecialityDto;
import com.nure.kravchenko.student.reference.entity.Speciality;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpecialityToSpecialityDtoConverterTest {

    private static final Long ID = 11L;

    private static final Integer NUMBER = 121;

    private static final String NAME = "Speciality name";

    private static final String SHORT_NAME = "PI";

    private static final String EDUCATIONAL_PROGRAM = "Educational program";

    private final SpecialityToSpecialityDtoConverter converter = new SpecialityToSpecialityDtoConverter();


    @Test
    void convert() {
        //GIVEN
        Speciality speciality = Speciality.builder()
                .id(ID)
                .number(NUMBER)
                .name(NAME)
                .shortName(SHORT_NAME)
                .educationalProgram(EDUCATIONAL_PROGRAM)
                .build();

        SpecialityDto expected = SpecialityDto.builder()
                .id(ID)
                .number(NUMBER)
                .name(NAME)
                .shortName(SHORT_NAME)
                .educationalProgram(EDUCATIONAL_PROGRAM)
                .build();

        //WHEN
        SpecialityDto actual = converter.convert(speciality);

        //THEN
        assert actual != null;
        assertEquals(expected, actual);
    }
}