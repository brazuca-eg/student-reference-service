package com.nure.kravchenko.student.reference.converter.request;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Worker;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class RequestToRequestDtoConverter implements Converter<Request, RequestDto> {

    @Override
    public RequestDto convert(@NonNull Request request) {
        RequestDto requestDto = new RequestDto();
        Reason reason = request.getReason();
        Worker worker;
        if (Objects.nonNull(request.getWorker())) {
            worker = request.getWorker();
            String workerFullName = worker.getSurname() + " " + worker.getName() + " " + worker.getFatherhood();
            requestDto.setWorkerEmail(worker.getEmail());
            requestDto.setWorkerFullName(workerFullName);
            requestDto.setWorkerJobTitle(worker.getJobTitle());
        }

        requestDto.setId(request.getId());
        requestDto.setStartDate(request.getStartDate());
        requestDto.setEndDate(request.getEndDate());
        requestDto.setS3FileName(request.getS3FileName());
        requestDto.setApproved(request.isApproved());
        requestDto.setComment(request.getComment());
        requestDto.setReasonName(reason.getName());
        requestDto.setReasonDescription(reason.getDescription());

        return requestDto;
    }

}
