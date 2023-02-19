package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;
import com.nure.kravchenko.student.reference.repository.ReasonRepository;
import com.nure.kravchenko.student.reference.repository.RequestRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RequestService implements IRequestService {

    private final RequestRepository requestRepository;

    private final ReasonRepository reasonRepository;

    public RequestService(RequestRepository requestRepository, ReasonRepository reasonRepository) {
        this.requestRepository = requestRepository;
        this.reasonRepository = reasonRepository;
    }

    @Override
    public Request createRequest(Student student, CreateRequestPayload requestPayload) {
        //check ticket from requestPayload
        String reasonName = requestPayload.getReasonName();
        Request request = new Request();
        if(StringUtils.isNoneBlank(reasonName)){
            reasonRepository.findReasonByName(reasonName).stream().findFirst().ifPresent(request::setReason);
        }
        if(request.getReason() != null){
            request.setStudent(student);
            request.setStartDate(LocalDateTime.now());
            return requestRepository.save(request);
        }

        throw new NotFoundException("Error while creating request");
    }
}
