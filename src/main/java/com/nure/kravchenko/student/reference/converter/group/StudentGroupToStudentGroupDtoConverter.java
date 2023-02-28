package com.nure.kravchenko.student.reference.converter.group;

import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StudentGroupToStudentGroupDtoConverter  implements Converter<StudentGroup, StudentGroupDto> {

    @Override
    public StudentGroupDto convert(@NonNull StudentGroup studentGroup) {
        return StudentGroupDto.builder()
                .id(studentGroup.getId())
                .name(studentGroup.getName())
                .startYear(studentGroup.getStartYear())
                .endYear(studentGroup.getEndYear())
                .learnForm(studentGroup.getLearnForm())
                .degreeForm(studentGroup.getDegreeForm())
                .build();
    }

}
