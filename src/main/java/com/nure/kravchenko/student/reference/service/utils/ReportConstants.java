package com.nure.kravchenko.student.reference.service.utils;

import java.time.format.DateTimeFormatter;

public class ReportConstants {

    private ReportConstants() {
        //private constructor for ReportConstants class.
    }

    public static final String FULL_NAME = "fullName";

    public static final String GENDER = "gender";

    public static final String STUDENT_GENDER = "studentGender";

    public static final String COURSE_NUMBER = "courseNumber";

    public static final String LEARN_FORM = "learnForm";

    public static final String FACULTY = "faculty";

    public static final String SPECIALITY = "speciality";

    public static final String EDUCATIONAL_PROGRAM = "educationalProgram";

    public static final String DEGREE_FORM = "degreeForm";

    public static final String START_DATE = "startDate";

    public static final String END_DATE = "endDate";

    public static final String REASON = "reason";

    public static final String REPORT_DATE = "reportDate";

    public static final String WORKER_INFO = "workerInfo";

    public static final String IMAGE = "image";

    public static final String HTML_EXTENSION = ".html";

    public static final String PDF_EXTENSION = ".pdf";

    public static final String JPG_EXTENSION = "jpg";

    public static final String REPORT_LANGUAGE = "RU";

    public static final String REPORT_ENCODING = "UTF-8";

    public static final String REPORT_FONT = "static/verdana.ttf";

    public static final DateTimeFormatter REPORT_DATE_PATTERN = DateTimeFormatter.ofPattern("dd.MM.yyyy");

}
