package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.SpecialityDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.entity.Speciality;
import com.nure.kravchenko.student.reference.payload.admin.CreateSpecialityDto;

import java.util.List;

public interface SpecialityService {

    Speciality findById(Long id);

    List<SpecialityDto> findAll();


    SpecialityDto createSpeciality(CreateSpecialityDto createSpecialityDto, Faculty faculty);

}
