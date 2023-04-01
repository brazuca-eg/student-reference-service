package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Speciality;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.payload.admin.CreateGroupDto;

import java.util.List;

public interface StudentGroupService {

    StudentGroup findById(Long id);

    List<StudentGroupDto> findAll();

    StudentGroup findGroupByName(String name);

    StudentGroupDto createGroup(CreateGroupDto createGroupDto, Speciality speciality);

}
