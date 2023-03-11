package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final ConversionService conversionService;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository, ConversionService conversionService) {
        this.facultyRepository = facultyRepository;
        this.conversionService = conversionService;
    }

    public List<FacultyDto> getAllFaculties(){
        return facultyRepository.findAll()
                .stream()
                .map(faculty -> conversionService.convert(faculty, FacultyDto.class))
                .collect(Collectors.toList());
    }

    public Faculty findById(Long id){
        Optional<Faculty> optionalRequest = facultyRepository.findById(id);
        if (optionalRequest.isPresent()) {
            return optionalRequest.get();
        }
        throw new NotFoundException("There are problems with faculty id");
    }
}
