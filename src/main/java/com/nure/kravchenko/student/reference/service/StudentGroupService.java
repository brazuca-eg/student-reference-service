package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
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

}
