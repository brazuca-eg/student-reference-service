package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.entity.app.RequestType;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.repository.RequestRepository;
import com.nure.kravchenko.student.reference.repository.StudentRepository;
import com.nure.kravchenko.student.reference.service.report.ReportService;
import com.nure.kravchenko.student.reference.service.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final ReportService reportService;

    private final RequestRepository requestRepository;

    private final ConversionService conversionService;

    @Autowired
    public StudentService(StudentRepository studentRepository, ReportService reportService, RequestRepository requestRepository, ConversionService conversionService) {
        this.studentRepository = studentRepository;
        this.reportService = reportService;
        this.requestRepository = requestRepository;
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
    public StudentDto getStudentDto(Student student) {
        if (Objects.nonNull(student)) {
            return conversionService.convert(student, StudentDto.class);
        }
        throw new NotFoundException("There are problems with user");
    }

    @Override
    public Student findByEmail(String email) {
        Student student = studentRepository.findByEmail(email);
        if (Objects.nonNull(student)) {
            return student;
        }
        return null;
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

    @Override
    public List<RequestDto> getStudentRequests(Long id, RequestType requestType, String requestFilter) {
        Student student = findStudentById(id);
        List<Request> requests = student.getRequests();

        if(requests.isEmpty()){
            return Collections.emptyList();
        }

        List<Request> resultRequests;
        switch (requestType) {
            case NEW:
                resultRequests = requests.stream().filter(request -> request.getEndDate() == null)
                        .collect(Collectors.toList());
                break;
            case APPROVED:
                resultRequests = requests.stream().filter(Request::isApproved)
                        .collect(Collectors.toList());
                break;
            case DENIED:
                resultRequests = requests.stream().filter(request -> !request.isApproved())
                        .collect(Collectors.toList());
                break;
            default:
                throw new RuntimeException("not valid request type provided");
        }

        if(requestFilter.equalsIgnoreCase("reasonName")){
            requests.sort(Comparator.comparing(r -> r.getReason().getName()));
        }

        return resultRequests.stream()
                .map(request -> conversionService.convert(request, RequestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDto> getWaitingApproveStudents() {
        List<Student> students = studentRepository.findWaitingApprovalStudents();
        return students.stream()
                .map(student -> conversionService.convert(student, StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto approveStudentRegistration(Student student, StudentGroup studentGroup, Ticket ticket) {
        if(!student.isApproved()){
            student.setStudentGroup(studentGroup);
            student.setTicket(ticket);
            student.setApproved(true);
            Student updated = save(student);
            return conversionService.convert(updated, StudentDto.class);
        }
        throw new NotFoundException("The user is already approved");
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
