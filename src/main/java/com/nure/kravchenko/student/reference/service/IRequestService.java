package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;

public interface IRequestService {

    Request createRequest(Student student, CreateRequestPayload requestPayload);

    Request findById(Long id);

    Request approveRequest(Worker worker, Request request, Boolean approved);
}
