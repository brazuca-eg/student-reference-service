package com.nure.kravchenko.student.reference.converter.student;

import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.app.Role;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationDtoToStudentConverterTest {

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String FATHERHOOD = "Fatherhood";

    private static final String EMAIL = "email_em@nure.ua";

    private static final String PASSWORD = "Password";

    private static final boolean APPROVED = false;

    private static final Character GENDER = 'M';

    private final RegistrationDtoToStudentConverter converter = new RegistrationDtoToStudentConverter();

    @Test
    void convert() {
        //GIVEN
        RegistrationDto registrationDto = RegistrationDto.builder()
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .password(PASSWORD)
                .gender(GENDER)
                .role(Role.STUDENT.name())
                .build();

        Student expected = Student.builder()
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .password(PASSWORD)
                .gender(GENDER)
                .approved(APPROVED)
                .build();

        //WHEN
        Student actual = converter.convert(registrationDto);
        actual.setPassword(PASSWORD);

        //THEN
        assertEquals(expected, actual);
    }
}