package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;
import com.nure.kravchenko.student.reference.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestService implements IRequestService {

    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request createRequest(Student student, CreateRequestPayload requestPayload) {
        //check ticket from requestPayload
        Request request = new Request();
        request.setStudent(student);
       // request.setReason(requestPayload.getReason());
        request.setStartDate(LocalDateTime.now());
        return requestRepository.save(request);
    }
}
