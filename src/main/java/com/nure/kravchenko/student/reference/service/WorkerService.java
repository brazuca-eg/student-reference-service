package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.payload.admin.ApproveWorkerDto;

import java.util.List;

public interface WorkerService {

    Worker findWorkerById(Long id);

    Worker findByEmail(String email);

    WorkerDto getWorkerDtoById(Long id);

    List<WorkerDto> getWaitingApproveWorkers();

    WorkerDto approveWorker(Long id, ApproveWorkerDto approveWorkerDto, Faculty faculty);

    FacultyDto getWorkerFaculty(Long id);

    WorkerDto create(RegistrationDto registrationDto);

    Worker save(Worker worker);

}
