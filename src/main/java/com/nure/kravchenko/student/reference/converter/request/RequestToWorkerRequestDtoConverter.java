package com.nure.kravchenko.student.reference.converter.request;

import com.nure.kravchenko.student.reference.dto.WorkerRequestDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import org.springframework.core.convert.converter.Converter;

public class RequestToWorkerRequestDtoConverter implements Converter<Request, WorkerRequestDto> {
    @Override
    public WorkerRequestDto convert(Request request) {
        Student student = request.getStudent();
        String fullName = student.getName() + " " + student.getSurname() + " " + student.getFatherhood();
        Reason reason = request.getReason();

        return WorkerRequestDto.builder()
                .id(request.getId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .studentId(student.getId())
                .studentFullName(fullName)
                .s3FileName(request.getS3FileName())
                .groupName(student.getStudentGroup().getName())
                .reasonName(reason.getName())
                .reasonDescription(reason.getDescription())
                .build();
    }
}
