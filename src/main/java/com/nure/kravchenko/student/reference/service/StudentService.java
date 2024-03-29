package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.StudentGroupDto;
import com.nure.kravchenko.student.reference.dto.StudentToUniInfoDto;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
import com.nure.kravchenko.student.reference.entity.Ticket;
import com.nure.kravchenko.student.reference.entity.app.RequestType;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.payload.StudentLoginPayload;
import com.nure.kravchenko.student.reference.payload.admin.UpdateStudentStatusDto;
import com.nure.kravchenko.student.reference.payload.admin.UpdateStudentTicketDto;

import java.util.List;

public interface StudentService {

    StudentDto create(RegistrationDto registrationDto);

    StudentDto checkLogin(StudentLoginPayload loginPayload);

    Student findStudentById(Long id);

    StudentDto updateStudentTicket(Student student, UpdateStudentTicketDto updateStudentTicketDto);

    StudentDto updateStatus(Student student, UpdateStudentStatusDto updateStudentStatusDto);

    StudentDto getStudentDto(Student student);

    Student save(Student student);

    StudentGroupDto getStudentGroupByStudentId(Long id);

    List<StudentDto> getStudentsByGroup(String groupName);

    Student findByEmail(String email);

    List<RequestDto> getStudentRequests(Long id, RequestType requestType, String requestFilter);

    List<StudentDto> getWaitingApproveStudents();

    StudentDto approveStudentRegistration(Student student, StudentGroup studentGroup, Ticket ticket );

    StudentToUniInfoDto getStudentToUniInfo(Student student);
}
