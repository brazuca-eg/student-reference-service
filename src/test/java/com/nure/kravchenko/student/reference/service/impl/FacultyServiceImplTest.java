package com.nure.kravchenko.student.reference.service.impl;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.repository.FacultyRepository;
import com.nure.kravchenko.student.reference.service.FacultyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    private static final Long ID = 11L;

    private static final String NAME = "Name";

    private static final String SHORT_NAME = "Short Name";

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private ConversionService conversionService;

    private FacultyService facultyService;

    @BeforeEach
    public void setUp() {
        facultyService = new FacultyServiceImpl(facultyRepository, conversionService);
    }

    @Test
    void findByIdWhenExists() {
        //GIVEN
        Faculty faculty = Faculty.builder()
                .id(ID)
                .name(NAME)
                .shortName(SHORT_NAME)
                .build();

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        //WHEN
        Faculty actual = facultyService.findById(ID);

        //THEN
        assertEquals(faculty, actual);
        verify(facultyRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByIdWhenNotExists() {
        //GIVEN
        Optional<Faculty> facultyOptional = Optional.empty();

        when(facultyRepository.findById(anyLong())).thenReturn(facultyOptional);

        //THEN
        NotFoundException exception = assertThrows(NotFoundException.class, () -> facultyService.findById(ID));
        assertEquals("There are problems with faculty id", exception.getMessage());
    }

    @Test
    void getAllFaculties() {
        //GIVEN
        Faculty faculty = Faculty.builder()
                .id(ID)
                .name(NAME)
                .shortName(SHORT_NAME)
                .build();

        FacultyDto facultyDto = FacultyDto.builder()
                .id(ID)
                .name(NAME)
                .shortName(SHORT_NAME)
                .build();

        when(facultyRepository.findAll()).thenReturn(Collections.singletonList(faculty));
        when(conversionService.convert(faculty, FacultyDto.class)).thenReturn(facultyDto);

        //WHEN
        List<FacultyDto> actual = facultyService.getAllFaculties();

        //THEN
        assertEquals(1, actual.size());
        verify(facultyRepository, times(1)).findAll();
    }
}