package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.payload.admin.CreateFacultyDto;

import java.util.List;

public interface FacultyService {

    List<FacultyDto> getAllFaculties();

    Faculty findById(Long id);

    Faculty save(Faculty faculty);

    FacultyDto createFaculty(CreateFacultyDto createFacultyDto);

}
