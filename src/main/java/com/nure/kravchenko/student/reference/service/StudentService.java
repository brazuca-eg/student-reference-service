package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.CreateStudentPayload;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.repository.StudentRepository;
import com.nure.kravchenko.student.reference.service.report.ReportService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final ReportService reportService;

    public StudentService(StudentRepository studentRepository, ReportService reportService) {
        this.studentRepository = studentRepository;
        this.reportService = reportService;
    }

    @Override
    public StudentDto create(CreateStudentPayload createStudentPayload) {
        //validate payload
        Student student = Student.builder()
                .name(createStudentPayload.getName())
                .surname(createStudentPayload.getSurname())
                .fatherhood(createStudentPayload.getFatherhood())
                .email(createStudentPayload.getEmail())
                .password(createStudentPayload.getPassword())
                .build();

        Student created = studentRepository.save(student);

        return StudentDto.builder()
                .name(created.getName())
                .surname(created.getSurname())
                .fatherhood(created.getFatherhood())
                .email(created.getEmail())
                .build();
    }

    @Override
    public Student checkLogin(StudentLoginPayload loginPayload) {
        //validate loginPayload
        String email = loginPayload.getEmail();
        //validate email
        Student student = studentRepository.findByEmail(email);
        if(Objects.nonNull(student) && loginPayload.getPassword().equals(student.getPassword())){
//            return StudentDto.builder()
//                    .name(student.getName())
//                    .surname(student.getSurname())
//                    .fatherhood(student.getFatherhood())
//                    .email(student.getEmail())
//                    .build();
            return student;
        }
        throw new NotFoundException("Provided invalid data for login");
    }

    @Override
    public Student findStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()){
            return optionalStudent.get();
        }
        throw new NotFoundException("There are problems with user id");
    }
}
