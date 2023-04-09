package com.nure.kravchenko.student.reference.converter.worker;

import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.entity.app.Role;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationDtoToWorkerConverterTest {

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String FATHERHOOD = "Fatherhood";

    private static final String EMAIL = "email_em@nure.ua";

    private static final String PASSWORD = "Password";

    private static final boolean APPROVED = false;

    private static final Character GENDER = 'M';

    private final RegistrationDtoToWorkerConverter converter = new RegistrationDtoToWorkerConverter();

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
                .role(Role.WORKER.name())
                .build();

        Worker expected = Worker.builder()
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .password(PASSWORD)
                .gender(GENDER)
                .approved(APPROVED)
                .build();

        //WHEN
        Worker actual = converter.convert(registrationDto);
        actual.setPassword(PASSWORD);

        //THEN
        assertEquals(expected, actual);
    }
}