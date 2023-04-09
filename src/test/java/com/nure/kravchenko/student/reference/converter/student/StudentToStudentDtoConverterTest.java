package com.nure.kravchenko.student.reference.converter.student;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.entity.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentToStudentDtoConverterTest {

    private static final Long ID = 11L;

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String FATHERHOOD = "Fatherhood";

    private static final String EMAIL = "email_em@nure.ua";

    private static final String PASSWORD = "Password";

    private static final boolean APPROVED = false;

    private static final Character GENDER = 'M';

    private final StudentToStudentDtoConverter converter = new StudentToStudentDtoConverter();

    @Test
    void convert() {
        //GIVEN
        Student student = Student.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .password(PASSWORD)
                .gender(GENDER)
                .approved(APPROVED)
                .build();

        StudentDto expected = StudentDto.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .gender(GENDER)
                .build();


        //WHEN
        StudentDto actual = converter.convert(student);

        //THEN
        assertEquals(expected, actual);
    }
}