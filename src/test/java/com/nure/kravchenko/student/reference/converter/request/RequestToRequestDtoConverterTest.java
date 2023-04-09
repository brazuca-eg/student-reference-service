package com.nure.kravchenko.student.reference.converter.request;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Request;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestToRequestDtoConverterTest {

    private static final String REASON_NAME = "Reason name";

    private static final String REASON_DESCRIPTION = "Reason description";

    private static final Long ID = 11L;

    private static final LocalDateTime START_DATE = LocalDateTime.now();

    private static final LocalDateTime END_DATE = LocalDateTime.now();

    private static final String S3_FILE_NAME = "s3filename";

    private static final boolean APPROVED = true;

    private static final String COMMENT = "Comment";

    private final RequestToRequestDtoConverter converter = new RequestToRequestDtoConverter();

    @Test
    void convert() {
        //GIVEN
        Reason reason = Reason.builder()
                .name(REASON_NAME)
                .description(REASON_DESCRIPTION)
                .build();

        Request request = Request.builder()
                .id(ID)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .s3FileName(S3_FILE_NAME)
                .approved(APPROVED)
                .comment(COMMENT)
                .reason(reason)
                .build();

        RequestDto expected = RequestDto.builder()
                .id(ID)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .s3FileName(S3_FILE_NAME)
                .approved(APPROVED)
                .comment(COMMENT)
                .reasonName(reason.getName())
                .reasonDescription(reason.getDescription())
                .build();

        //WHEN
        RequestDto actual = converter.convert(request);

        //THEN
        assert actual != null;
        assertEquals(expected, actual);

    }
}