package com.nure.kravchenko.student.reference.converter.report;

import com.nure.kravchenko.student.reference.dto.ReportInformation;
import com.nure.kravchenko.student.reference.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

import static com.nure.kravchenko.student.reference.service.utils.ReportConstants.REPORT_DATE_PATTERN;

public class RequestToReportInformationConverter implements Converter<Request, ReportInformation> {

    @Override
    public ReportInformation convert(@NonNull Request request) {
        ReportInformation reportInformation = new ReportInformation();
        Student student = request.getStudent();
        reportInformation.setFullName(student.getSurname() + StringUtils.SPACE + student.getName() +
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
        int year = LocalDateTime.now().getYear() - studentGroup.getStartYear().getYear();
        if(year == 0){
            year = 1;
        }
        reportInformation.setCourseNumber(String.valueOf(year));
        String learnForm = studentGroup.getLearnForm();
        if (StringUtils.equalsIgnoreCase("Денна", learnForm)) {
            reportInformation.setLearnForm("денної");
        } else {
            reportInformation.setLearnForm("заочної");
        }
        reportInformation.setDegreeForm(studentGroup.getDegreeForm());
        reportInformation.setStartDate(studentGroup.getStartYear().format(REPORT_DATE_PATTERN));
        reportInformation.setEndDate(studentGroup.getEndYear().format(REPORT_DATE_PATTERN));
        reportInformation.setReportDate(request.getEndDate().format(REPORT_DATE_PATTERN));

        Speciality speciality = studentGroup.getSpeciality();
        reportInformation.setSpeciality(speciality.getNumber() + StringUtils.SPACE + speciality.getName());
        reportInformation.setEducationalProgram(speciality.getEducationalProgram());
        Faculty faculty = speciality.getFaculty();
        reportInformation.setFaculty(faculty.getName());

        Worker worker = request.getWorker();
        String jobTitle = worker.getJobTitle().toLowerCase();
        reportInformation.setWorkerInfo(jobTitle + StringUtils.SPACE + worker.getSurname() + StringUtils.SPACE + worker.getName() +
                StringUtils.SPACE + worker.getFatherhood());

        return reportInformation;
    }

}
