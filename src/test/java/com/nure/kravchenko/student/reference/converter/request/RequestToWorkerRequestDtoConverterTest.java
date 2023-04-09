package com.nure.kravchenko.student.reference.converter.request;

import com.nure.kravchenko.student.reference.dto.WorkerRequestDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestToWorkerRequestDtoConverterTest {

    private static final String GROUP_NAME = "Group name";

    private static final String STUDENT_NAME = "Name";

    private static final String STUDENT_SURNAME = "Surname";

    private static final String STUDENT_FATHERHOOD = "Fatherhood";

    private static final String REASON_NAME = "Reason name";

    private static final String REASON_DESCRIPTION = "Reason description";

    private static final Long ID = 11L;

    private static final LocalDateTime START_DATE = LocalDateTime.now();

    private static final LocalDateTime END_DATE = LocalDateTime.now();

    private static final String S3_FILE_NAME = "s3filename";

    private static final boolean APPROVED = true;

    private static final String COMMENT = "Comment";

    private final RequestToWorkerRequestDtoConverter converter = new RequestToWorkerRequestDtoConverter();

    @Test
    void convert() {
        //GIVEN
        Reason reason = Reason.builder()
                .name(REASON_NAME)
                .description(REASON_DESCRIPTION)
                .build();

        StudentGroup studentGroup = StudentGroup.builder()
                .name(GROUP_NAME)
                .build();

        Student student = Student.builder()
                .name(STUDENT_NAME)
                .surname(STUDENT_SURNAME)
                .fatherhood(STUDENT_FATHERHOOD)
                .studentGroup(studentGroup)
                .build();
        String fullName = student.getSurname() + " " + student.getName() + " " + student.getFatherhood();

        Request request = Request.builder()
                .id(ID)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .s3FileName(S3_FILE_NAME)
                .approved(APPROVED)
                .comment(COMMENT)
                .reason(reason)
                .student(student)
                .build();

        WorkerRequestDto expected = WorkerRequestDto.builder()
                .id(request.getId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .studentId(student.getId())
                .studentFullName(fullName)
                .s3FileName(request.getS3FileName())
                .approved(request.isApproved())
                .comment(request.getComment())
                .groupName(student.getStudentGroup().getName())
                .reasonName(reason.getName())
                .reasonDescription(reason.getDescription())
                .build();

        //WHEN
        WorkerRequestDto actual = converter.convert(request);

        //THEN
        assert actual != null;
        assertEquals(expected, actual);
    }
}