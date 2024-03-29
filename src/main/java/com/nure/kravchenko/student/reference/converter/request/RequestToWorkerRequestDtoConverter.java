package com.nure.kravchenko.student.reference.converter.request;

import com.nure.kravchenko.student.reference.dto.WorkerRequestDto;
import com.nure.kravchenko.student.reference.entity.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class RequestToWorkerRequestDtoConverter implements Converter<Request, WorkerRequestDto> {

    @Override
    public WorkerRequestDto convert(@NonNull Request request) {
        Student student = request.getStudent();
        StudentGroup studentGroup = student.getStudentGroup();
        Speciality speciality = studentGroup.getSpeciality();

        String fullName = student.getSurname() + " " + student.getName() + " " + student.getFatherhood();
        Reason reason = request.getReason();

        return WorkerRequestDto.builder()
                .id(request.getId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .studentId(student.getId())
                .studentFullName(fullName)
                .s3FileName(request.getS3FileName())
                .approved(request.isApproved())
                .comment(request.getComment())
                .groupName(studentGroup.getName())
                .specialityName(speciality.getName())
                .educationalProgram(speciality.getEducationalProgram())
                .reasonName(reason.getName())
                .reasonDescription(reason.getDescription())
                .build();
    }
}
