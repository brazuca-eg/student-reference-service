package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.SpecialityDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Speciality;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.admin.CreateSpecialityDto;
import com.nure.kravchenko.student.reference.repository.SpecialityRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialityService {

    private final SpecialityRepository specialityRepository;

    private final ConversionService conversionService;


    public SpecialityService(SpecialityRepository specialityRepository, ConversionService conversionService) {
        this.specialityRepository = specialityRepository;
        this.conversionService = conversionService;
    }

    public Speciality findById(Long id) {
        Optional<Speciality> optionalSpeciality = specialityRepository.findById(id);
        if (optionalSpeciality.isPresent()) {
            return optionalSpeciality.get();
        }
        throw new NotFoundException("There are problems with speciality id");
    }

    public List<SpecialityDto> findAll() {
        return specialityRepository.findAll()
                .stream()
                .map(speciality -> conversionService.convert(speciality, SpecialityDto.class))
                .collect(Collectors.toList());
    }


    public SpecialityDto createSpeciality(CreateSpecialityDto createSpecialityDto, Faculty faculty) {
        Speciality speciality = conversionService.convert(createSpecialityDto, Speciality.class);
        speciality.setFaculty(faculty);
        return conversionService.convert(specialityRepository.save(speciality), SpecialityDto.class);
    }

}
