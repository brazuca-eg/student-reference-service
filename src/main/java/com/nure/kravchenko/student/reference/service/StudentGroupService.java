package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Speciality;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.admin.CreateGroupDto;
import com.nure.kravchenko.student.reference.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;

    private final ConversionService conversionService;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository, ConversionService conversionService) {
        this.studentGroupRepository = studentGroupRepository;
        this.conversionService = conversionService;
    }

    public StudentGroup findById(Long id) {
        Optional<StudentGroup> optionalStudentGroup = studentGroupRepository.findById(id);
        if (optionalStudentGroup.isPresent()) {
            return optionalStudentGroup.get();
        }
        throw new NotFoundException("There are problems with group id");
    }

    public StudentGroup findGroupByName(String name) {
        Optional<StudentGroup> optionalStudentGroup = studentGroupRepository.findGroupByName(name);
        if (optionalStudentGroup.isPresent()) {
            return optionalStudentGroup.get();
        }
        throw new NotFoundException("There are problems with group name");
    }

    public StudentGroupDto createGroup(CreateGroupDto createGroupDto, Speciality speciality) {
        StudentGroup studentGroup = conversionService.convert(createGroupDto, StudentGroup.class);
        studentGroup.setSpeciality(speciality);
        return conversionService.convert(studentGroupRepository.save(studentGroup), StudentGroupDto.class);
    }

}
