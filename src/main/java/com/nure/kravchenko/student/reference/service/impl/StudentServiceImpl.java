package com.nure.kravchenko.student.reference.service.impl;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.dto.StudentToUniInfoDto;
import com.nure.kravchenko.student.reference.entity.*;
import com.nure.kravchenko.student.reference.entity.app.RequestType;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.repository.RequestRepository;
import com.nure.kravchenko.student.reference.repository.StudentRepository;
import com.nure.kravchenko.student.reference.service.StudentService;
import com.nure.kravchenko.student.reference.service.report.ReportService;
import com.nure.kravchenko.student.reference.service.utils.FindSortingComparatorUtil;
import com.nure.kravchenko.student.reference.service.utils.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ReportService reportService;

    private final RequestRepository requestRepository;

    private final ConversionService conversionService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ReportService reportService, RequestRepository requestRepository, ConversionService conversionService) {
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
    public List<RequestDto> getStudentRequests(Long id, RequestType requestType, String filter) {
        Student student = findStudentById(id);
        List<Request> requests = student.getRequests();

        if (requests.isEmpty()) {
            return Collections.emptyList();
        }

        List<Request> resultRequests;
        switch (requestType) {
            case NEW:
                resultRequests = requests.stream()
                        .filter(request -> request.getEndDate() == null)
                        .sorted(Comparator.comparing(Request::getStartDate).reversed())
                        .collect(Collectors.toList());
                break;
            case APPROVED:
                resultRequests = requests.stream()
                        .filter(Request::isApproved)
                        .sorted(Comparator.comparing(Request::getEndDate).reversed())
                        .collect(Collectors.toList());
                break;
            case DENIED:
                resultRequests = requests.stream()
                        .filter(request -> !request.isApproved())
                        .filter(request -> request.getEndDate() != null)
                        .sorted(Comparator.comparing(Request::getEndDate).reversed())
                        .collect(Collectors.toList());
                break;
            default:
                throw new RuntimeException("not valid request type provided");
        }

        Comparator<Request> comparator = FindSortingComparatorUtil.findSortingComparator(filter);


        return resultRequests.stream()
                .sorted(comparator)
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
        if (!student.isApproved()) {
            student.setStudentGroup(studentGroup);
            student.setTicket(ticket);
            student.setApproved(true);
            Student updated = save(student);
            return conversionService.convert(updated, StudentDto.class);
        }
        throw new NotFoundException("The user is already approved");
    }

    @Override
    public StudentToUniInfoDto getStudentToUniInfo(Student student) {
        StudentToUniInfoDto studentToUniInfoDto = new StudentToUniInfoDto();

        StudentGroup studentGroup = student.getStudentGroup();
        if (Objects.nonNull(studentGroup)) {
            studentToUniInfoDto.setGroupName(studentGroup.getName());
            studentToUniInfoDto.setDegreeForm(studentGroup.getDegreeForm());
            studentToUniInfoDto.setLearnForm(studentGroup.getLearnForm());
            studentToUniInfoDto.setGroupStartYear(studentGroup.getStartYear());
            studentToUniInfoDto.setGroupEndYear(studentGroup.getEndYear());

            Speciality speciality = studentGroup.getSpeciality();
            studentToUniInfoDto.setSpecialityName(speciality.getNumber() + StringUtils.SPACE + speciality.getName());
            studentToUniInfoDto.setEducationalProgram(speciality.getEducationalProgram());
            Faculty faculty = speciality.getFaculty();
            studentToUniInfoDto.setFacultyName(faculty.getName());
        }

        return studentToUniInfoDto;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
