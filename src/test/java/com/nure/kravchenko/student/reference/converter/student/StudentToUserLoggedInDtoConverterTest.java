package com.nure.kravchenko.student.reference.converter.student;

import com.nure.kravchenko.student.reference.dto.UserLoggedInDto;
import com.nure.kravchenko.student.reference.entity.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentToUserLoggedInDtoConverterTest {

    private static final Long ID = 11L;

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String FATHERHOOD = "Fatherhood";

    private static final String EMAIL = "email_em@nure.ua";

    private static final String PASSWORD = "Password";

    private static final boolean APPROVED = false;

    private static final Character GENDER = 'M';

    private final StudentToUserLoggedInDtoConverter converter = new StudentToUserLoggedInDtoConverter();

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

        UserLoggedInDto expected = UserLoggedInDto.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .gender(GENDER)
                .approved(APPROVED)
                .build();

        //WHEN
        UserLoggedInDto actual = converter.convert(student);

        //THEN
        assertEquals(expected, actual);
    }

}