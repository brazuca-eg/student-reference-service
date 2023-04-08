package com.nure.kravchenko.student.reference.converter.reason;

import com.nure.kravchenko.student.reference.dto.ReasonDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReasonToReasonDtoConverterTest {

    private static final Long ID = 11L;

    private static final String NAME = "Reason name";

    private static final String DESCRIPTION = "Description";

    private final ReasonToReasonDtoConverter converter = new ReasonToReasonDtoConverter();

    @Test
    void convert() {
        //GIVEN
        Reason reason = Reason.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        ReasonDto expected =  ReasonDto.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        //WHEN
        ReasonDto actual = converter.convert(reason);

        //THEN
        assert actual != null;
        assertEquals(expected, actual);

    }
}