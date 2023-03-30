package com.nure.kravchenko.student.reference.converter.speciality;

import com.nure.kravchenko.student.reference.dto.SpecialityDto;
import com.nure.kravchenko.student.reference.entity.Speciality;
import org.springframework.core.convert.converter.Converter;

public class SpecialityToSpecialityDtoConverter implements Converter<Speciality, SpecialityDto> {

    @Override
    public SpecialityDto convert(Speciality speciality) {
        return SpecialityDto.builder()
                .id(speciality.getId())
                .name(speciality.getName())
                .shortName(speciality.getShortName())
                .number(speciality.getNumber())
                .educationalProgram(speciality.getEducationalProgram())
                .build();
    }

}
