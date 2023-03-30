package com.nure.kravchenko.student.reference.converter.report;

import com.nure.kravchenko.student.reference.dto.ReportInformation;
import com.nure.kravchenko.student.reference.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestToReportInformationConverter implements Converter<Request, ReportInformation> {

    @Override
    public ReportInformation convert(Request request) {
        ReportInformation reportInformation = new ReportInformation();
        Student student = request.getStudent();
        reportInformation.setFullName(student.getName() + StringUtils.SPACE + student.getSurname() +
                StringUtils.SPACE + student.getFatherhood());
        if (student.getGender() == 'M') {
            reportInformation.setGender("він");
            reportInformation.setStudentGender("студентом");
        } else {
            reportInformation.setGender("вона");
            reportInformation.setStudentGender("студенткою");
        }

        reportInformation.setReason(request.getReason().getName());
        StudentGroup studentGroup = student.getStudentGroup();
        reportInformation.setCourseNumber(String
                .valueOf(LocalDateTime.now().getYear() - studentGroup.getStartYear().getYear()));
        String learnForm = studentGroup.getLearnForm();
        if (StringUtils.equalsIgnoreCase("Денна", learnForm)) {
            reportInformation.setLearnForm("денної");
        } else {
            reportInformation.setLearnForm("заочної");
        }
        reportInformation.setDegreeForm(studentGroup.getDegreeForm());
        reportInformation.setStartDate(studentGroup.getStartYear().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        reportInformation.setEndDate(studentGroup.getEndYear().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        reportInformation.setReportDate(request.getEndDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Speciality speciality = studentGroup.getSpeciality();
        reportInformation.setSpeciality(speciality.getNumber() + StringUtils.SPACE + speciality.getName());
        reportInformation.setEducationalProgram(speciality.getEducationalProgram());
        Faculty faculty = speciality.getFaculty();
        reportInformation.setFaculty(faculty.getName());

        return reportInformation;
    }

}
