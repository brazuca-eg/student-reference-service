package com.nure.kravchenko.student.reference.converter.report;

import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.Worker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.format.DateTimeFormatter;

public class RequestToApprovedMessageBodyConverter implements Converter<Request, String> {

    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convert(Request request) {
        String greetingsPart = "Доброго дня!\n";
        Student student = request.getStudent();
        String fullName = student.getName() + StringUtils.SPACE +
                student.getSurname() + StringUtils.SPACE + student.getFatherhood();
        String endDate = request.getEndDate().format(CUSTOM_FORMATTER);
        Worker worker = request.getWorker();
        String workerJobTitle = worker.getJobTitle();
        String workerFullName = worker.getName() + StringUtils.SPACE +
                worker.getSurname() + StringUtils.SPACE + worker.getFatherhood();
        String workerEmail = worker.getEmail();

        if (request.isApproved()) {
            String studentPart = "Довідка була підтверджена для студента: " + fullName + ".\n";
            String datePart = "Файл довідки був прикріплений до цього листа. " +
                    "Дата надання довідки: " + endDate + "\n";


            String workerInfoPart = "Надання довідки підтвердив:" + workerJobTitle + " - " + workerFullName + "\n";

            String questionPart = "Якщо у Вас залишились запитання звертайтеся до робітника деканату, " +
                    "який підтвердив видачу довідки про навчання за його робочим email - " + workerEmail;

            return greetingsPart +
                    studentPart +
                    datePart +
                    workerInfoPart +
                    questionPart;
        } else {
            String studentPart = "Довідка була відхилена для надання для студента: " + fullName + ".\n";
            String datePart = "Дата відхилення довідки: " + endDate + "\n";
            String workerInfoPart = "Надання довідки відхилив:" + workerJobTitle + " - " + workerFullName + "\n";

            String deniedReasonPart = "Причина відхилення довідки, яку вказав робітник деканату: "
                    + request.getComment() + "\n";
            String questionPart = "Якщо у Вас залишились запитання звертайтеся до робітника деканату, " +
                    "який відхилив видачу довідки про навчання за його робочим email - " + workerEmail;

            return greetingsPart +
                    studentPart +
                    datePart +
                    workerInfoPart +
                    deniedReasonPart +
                    questionPart;
        }
    }

}
