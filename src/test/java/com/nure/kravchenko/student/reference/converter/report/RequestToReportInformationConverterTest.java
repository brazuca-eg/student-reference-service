package com.nure.kravchenko.student.reference.converter.report;

import com.nure.kravchenko.student.reference.dto.ReportInformation;
import com.nure.kravchenko.student.reference.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.nure.kravchenko.student.reference.service.utils.ReportConstants.REPORT_DATE_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestToReportInformationConverterTest {

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String FATHERHOOD = "Fatherhood";

    private static final String EMAIL = "email_em@nure.ua";

    private static final String PASSWORD = "Password";

    private static final boolean APPROVED = false;

    private static final Character GENDER = 'M';

    private static final String GROUP_NAME = "Group name";

    private static final String DEGREE_FORM = "Degree form";

    private static final String LEARN_FORM = "Learn form";

    private static final LocalDate GROUP_START_YEAR = LocalDate.of(2019, 8, 1);

    private static final LocalDate GROUP_END_YEAR = LocalDate.of(2023, 6, 30);

    private static final Long REQUEST_ID = 11L;

    private static final LocalDateTime REQUEST_START_DATE = LocalDateTime.now();

    private static final LocalDateTime REQUEST_END_DATE = LocalDateTime.now();

    private static final String S3_FILE_NAME = "s3filename";

    private static final String COMMENT = "Comment";

    private static final String REASON_NAME = "Reason name";

    private static final String REASON_DESCRIPTION = "Reason description";

    private static final Integer SPECIALITY_NUMBER = 121;

    private static final String SPECIALITY_NAME = "Speciality name";

    private static final String SPECIALITY_SHORT_NAME = "PI";

    private static final String SPECIALITY_EDUCATIONAL_PROGRAM = "Educational program";

    private static final String FACULTY_NAME = "Name";
    private static final String FACULTY_SHORT_NAME = "Short Name";

    private final RequestToReportInformationConverter converter = new RequestToReportInformationConverter();

    @Test
    void convert() {
        //GIVEN
        Faculty faculty = Faculty.builder()
                .name(FACULTY_NAME)
                .shortName(FACULTY_SHORT_NAME)
                .build();

        Speciality speciality = Speciality.builder()
                .number(SPECIALITY_NUMBER)
                .name(SPECIALITY_NAME)
                .shortName(SPECIALITY_SHORT_NAME)
                .educationalProgram(SPECIALITY_EDUCATIONAL_PROGRAM)
                .faculty(faculty)
                .build();

        StudentGroup studentGroup = StudentGroup.builder()
                .name(GROUP_NAME)
                .degreeForm(DEGREE_FORM)
                .learnForm(LEARN_FORM)
                .startYear(GROUP_START_YEAR)
                .endYear(GROUP_END_YEAR)
                .speciality(speciality)
                .build();

        Student student = Student.builder()
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .password(PASSWORD)
                .gender(GENDER)
                .approved(APPROVED)
                .studentGroup(studentGroup)
                .build();

        Reason reason = Reason.builder()
                .name(REASON_NAME)
                .description(REASON_DESCRIPTION)
                .build();


        Request request = Request.builder()
                .id(REQUEST_ID)
                .startDate(REQUEST_START_DATE)
                .endDate(REQUEST_END_DATE)
                .s3FileName(S3_FILE_NAME)
                .approved(APPROVED)
                .comment(COMMENT)
                .reason(reason)
                .student(student)
                .build();

        ReportInformation expected = ReportInformation.builder()
                .fullName(student.getSurname() + StringUtils.SPACE + student.getName() +
                        StringUtils.SPACE + student.getFatherhood())
                .gender("він")
                .studentGender("студентом")
                .courseNumber("4")
                .learnForm("заочної")
                .faculty(FACULTY_NAME)
                .speciality(SPECIALITY_NUMBER + StringUtils.SPACE + SPECIALITY_NAME)
                .educationalProgram(SPECIALITY_EDUCATIONAL_PROGRAM)
                .degreeForm(DEGREE_FORM)
                .startDate("2019-08-01")
                .endDate("2023-06-30")
                .reason(REASON_NAME)
                .reportDate(LocalDateTime.now().format(REPORT_DATE_PATTERN))
                .build();

        //WHEN
        ReportInformation actual = converter.convert(request);

        //THEN
        assertEquals(expected, actual);
    }
}