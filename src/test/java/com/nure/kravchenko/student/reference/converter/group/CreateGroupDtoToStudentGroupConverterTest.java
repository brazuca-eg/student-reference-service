package com.nure.kravchenko.student.reference.converter.group;

import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.payload.admin.CreateGroupDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateGroupDtoToStudentGroupConverterTest {

    private static final String NAME = "Group name";

    private static final String DEGREE_FORM = "Degree form";

    private static final String LEARN_FORM = "Learn form";

    private static final LocalDate START_YEAR = LocalDate.of(2019, 8, 1);

    private static final LocalDate END_YEAR = LocalDate.of(2023, 6, 30);

    private final CreateGroupDtoToStudentGroupConverter converter = new CreateGroupDtoToStudentGroupConverter();

    @Test
    void convert() {
        //GIVEN
        StudentGroup expected = StudentGroup.builder()
                .name(NAME)
                .degreeForm(DEGREE_FORM)
                .learnForm(LEARN_FORM)
                .startYear(START_YEAR)
                .endYear(END_YEAR)
                .build();

        CreateGroupDto createGroupDto = CreateGroupDto.builder()
                .name(NAME)
                .degreeForm(DEGREE_FORM)
                .learnForm(LEARN_FORM)
                .startYear(START_YEAR)
                .endYear(END_YEAR)
                .build();

        //WHEN
        StudentGroup actual = converter.convert(createGroupDto);

        //THEN
        assert actual != null;
        assertEquals(expected, actual);
    }
}