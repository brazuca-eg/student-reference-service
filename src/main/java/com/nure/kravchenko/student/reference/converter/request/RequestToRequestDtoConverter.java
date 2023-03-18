package com.nure.kravchenko.student.reference.converter.request;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Request;
import org.springframework.core.convert.converter.Converter;

public class RequestToRequestDtoConverter implements Converter<Request, RequestDto> {

    @Override
    public RequestDto convert(Request request) {
        Reason reason = request.getReason();

        return RequestDto.builder()
                .id(request.getId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .s3FileName(request.getS3FileName())
                .reasonName(reason.getName())
                .reasonDescription(reason.getDescription())
                .build();
    }

}
