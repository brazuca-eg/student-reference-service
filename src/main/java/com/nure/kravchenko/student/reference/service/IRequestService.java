package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.WorkerRequestDto;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.payload.CreateRequestDto;

import java.util.List;

public interface IRequestService {

    RequestDto createRequest(Student student, CreateRequestDto requestPayload);

    Request findById(Long id);

    RequestDto approveRequest(Worker worker, Request request, Boolean approved);

    List<WorkerRequestDto> findWaitingRequest(Worker worker);

    List<WorkerRequestDto> findAssignedWorkerRequests(Worker worker);
}
