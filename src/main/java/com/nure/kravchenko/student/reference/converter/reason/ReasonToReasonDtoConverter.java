package com.nure.kravchenko.student.reference.converter.reason;

import com.nure.kravchenko.student.reference.dto.ReasonDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import org.springframework.core.convert.converter.Converter;

public class ReasonToReasonDtoConverter implements Converter<Reason, ReasonDto> {

    @Override
    public ReasonDto convert(Reason reason) {
        return ReasonDto.builder()
                .id(reason.getId())
                .description(reason.getDescription())
                .name(reason.getName())
                .build();
    }

}
