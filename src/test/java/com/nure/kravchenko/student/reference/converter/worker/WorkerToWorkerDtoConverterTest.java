package com.nure.kravchenko.student.reference.converter.worker;

import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.entity.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkerToWorkerDtoConverterTest {

    private static final Long ID = 11L;

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String FATHERHOOD = "Fatherhood";

    private static final String EMAIL = "email_em@nure.ua";

    private static final String PASSWORD = "Password";

    private static final boolean APPROVED = false;

    private static final Character GENDER = 'M';

    private final WorkerToWorkerDtoConverter converter = new WorkerToWorkerDtoConverter();

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

        WorkerDto expected = WorkerDto.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .gender(GENDER)
                .build();


        //WHEN
        WorkerDto actual = converter.convert(worker);

        //THEN
        assertEquals(expected, actual);
    }
}