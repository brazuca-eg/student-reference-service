package com.nure.kravchenko.student.reference.converter.worker;

import com.nure.kravchenko.student.reference.dto.UserLoggedInDto;
import com.nure.kravchenko.student.reference.entity.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkerToUserLoggedInDtoConverterTest {

    private static final Long ID = 11L;

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String FATHERHOOD = "Fatherhood";

    private static final String EMAIL = "email_em@nure.ua";

    private static final String PASSWORD = "Password";

    private static final boolean APPROVED = false;

    private static final Character GENDER = 'M';

    private final WorkerToUserLoggedInDtoConverter converter = new WorkerToUserLoggedInDtoConverter();

    @Test
    void convert() {
        //GIVEN
        Worker worker = Worker.builder()
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
        UserLoggedInDto actual = converter.convert(worker);

        //THEN
        assertEquals(expected, actual);
    }
}