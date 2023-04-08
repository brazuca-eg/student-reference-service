package com.nure.kravchenko.student.reference.converter.group;

import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentGroupToStudentGroupDtoConverterTest {

    private static final Long ID = 11L;

    private static final String NAME = "Group name";

    private static final String DEGREE_FORM = "Degree form";

    private static final String LEARN_FORM = "Learn form";

    private static final LocalDate START_YEAR = LocalDate.of(2019, 8, 1);

    private static final LocalDate END_YEAR = LocalDate.of(2023, 6, 30);

    private final StudentGroupToStudentGroupDtoConverter converter = new StudentGroupToStudentGroupDtoConverter();

    @Test
    void convert() {
        //GIVEN
        StudentGroup studentGroup = StudentGroup.builder()
                .id(ID)
                .name(NAME)
                .degreeForm(DEGREE_FORM)
                .learnForm(LEARN_FORM)
                .startYear(START_YEAR)
                .endYear(END_YEAR)
                .build();

        StudentGroupDto expected = StudentGroupDto.builder()
                .id(ID)
                .name(NAME)
                .startYear(START_YEAR)
                .endYear(END_YEAR)
                .learnForm(LEARN_FORM)
                .degreeForm(DEGREE_FORM)
                .build();

        //WHEN
        StudentGroupDto actual = converter.convert(studentGroup);

        //THEN
        assert actual != null;
        assertEquals(expected, actual);
    }
}