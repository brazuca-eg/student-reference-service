package com.nure.kravchenko.student.reference.converter.group;

import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.payload.admin.CreateGroupDto;
import org.springframework.core.convert.converter.Converter;

public class CreateGroupDtoToStudentGroupConverter implements Converter<CreateGroupDto, StudentGroup> {

    @Override
    public StudentGroup convert(CreateGroupDto createGroupDto) {
        return StudentGroup.builder()
                .name(createGroupDto.getName())
                .degreeForm(createGroupDto.getDegreeForm())
                .learnForm(createGroupDto.getLearnForm())
                .startYear(createGroupDto.getStartYear())
                .endYear(createGroupDto.getEndYear())
                .build();
    }

}
