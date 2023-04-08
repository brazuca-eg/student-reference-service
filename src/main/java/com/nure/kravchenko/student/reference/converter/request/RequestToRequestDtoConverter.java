package com.nure.kravchenko.student.reference.converter.request;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Request;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class RequestToRequestDtoConverter implements Converter<Request, RequestDto> {

    @Override
    public RequestDto convert(@NonNull Request request) {
        Reason reason = request.getReason();

        return RequestDto.builder()
                .id(request.getId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .s3FileName(request.getS3FileName())
                .approved(request.isApproved())
                .comment(request.getComment())
                .reasonName(reason.getName())
                .reasonDescription(reason.getDescription())
                .build();
    }

}
