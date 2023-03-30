package com.nure.kravchenko.student.reference.converter.speciality;

import com.nure.kravchenko.student.reference.entity.Speciality;
import com.nure.kravchenko.student.reference.payload.admin.CreateSpecialityDto;
import org.springframework.core.convert.converter.Converter;

public class CreateSpecialityDtoToSpecialityConverter implements Converter<CreateSpecialityDto, Speciality> {
    @Override
    public Speciality convert(CreateSpecialityDto createSpecialityDto) {
        return Speciality.builder()
                .number(createSpecialityDto.getNumber())
                .name(createSpecialityDto.getName())
                .shortName(createSpecialityDto.getShortName())
                .educationalProgram(createSpecialityDto.getEducationalProgram())
                .build();
    }

}

