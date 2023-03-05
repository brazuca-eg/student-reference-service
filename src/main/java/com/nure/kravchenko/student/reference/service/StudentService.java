package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.repository.StudentRepository;
import com.nure.kravchenko.student.reference.service.report.ReportService;
import com.nure.kravchenko.student.reference.service.utils.ValidationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final ReportService reportService;

    private final ConversionService conversionService;

    public StudentService(StudentRepository studentRepository, ReportService reportService, ConversionService conversionService) {
        this.studentRepository = studentRepository;
        this.reportService = reportService;
        this.conversionService = conversionService;
    }

    @Override
    public StudentDto create(RegistrationDto registrationDto) {
        Student student = conversionService.convert(registrationDto, Student.class);

        assert student != null;
        Student created = studentRepository.save(student);

        return conversionService.convert(created, StudentDto.class);
    }

    @Override
    public StudentDto checkLogin(StudentLoginPayload loginPayload) {
        String email = loginPayload.getEmail();
        if (ValidationUtils.isValidEmailAddress(email)) {
            Student student = studentRepository.findByEmail(email);
            if (Objects.nonNull(student) && loginPayload.getPassword().equals(student.getPassword())) {
                return conversionService.convert(student, StudentDto.class);
            } else {
                throw new NotFoundException("Provided not existing email");
            }
        }
        throw new NotFoundException("Provided invalid email for login");
    }

    @Override
    public Student findStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        throw new NotFoundException("There are problems with user id");
    }

    @Override
    public StudentGroupDto getStudentGroupByStudentId(Long id) {
        Student student = findStudentById(id);
        StudentGroup studentGroup = student.getStudentGroup();
        if (Objects.nonNull(studentGroup)) {
            return conversionService.convert(studentGroup, StudentGroupDto.class);
        }
        throw new NotFoundException("The user doesn't consist in a group");
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
