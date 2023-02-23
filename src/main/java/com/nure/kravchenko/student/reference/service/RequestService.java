package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.*;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;
import com.nure.kravchenko.student.reference.repository.ReasonRepository;
import com.nure.kravchenko.student.reference.repository.RequestRepository;
import com.nure.kravchenko.student.reference.service.report.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class RequestService implements IRequestService {

    private final RequestRepository requestRepository;

    private final ReasonRepository reasonRepository;

    private final ReportService reportService;

    public RequestService(RequestRepository requestRepository, ReasonRepository reasonRepository, ReportService reportService) {
        this.requestRepository = requestRepository;
        this.reasonRepository = reasonRepository;
        this.reportService = reportService;
    }

    @Override
    public Request findById(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if(optionalRequest.isPresent()){
            return optionalRequest.get();
        }
        throw new NotFoundException("There are problems with request id");
    }

    @Override
    public Request createRequest(Student student, CreateRequestPayload requestPayload) {
        if (Objects.nonNull(student) && Objects.nonNull(student.getTicket())) {
            Ticket ticket = student.getTicket();
            if (StringUtils.equalsIgnoreCase(ticket.getNumber(), requestPayload.getNumber()) &&
                    StringUtils.equalsIgnoreCase(ticket.getSerialNumber(), requestPayload.getSerialNumber())) {
                Optional<Reason> reason = reasonRepository.findReasonByName(requestPayload.getReasonName());
                if (reason.isPresent()) {
                    Request request = new Request();
                    request.setStudent(student);
                    request.setStartDate(LocalDateTime.now());
                    request.setReason(reason.get());
                    return requestRepository.save(request);
                } else {
                    throw new NotFoundException("Invalid reason name provided");
                }
            } else {
                throw new NotFoundException("Invalid ticket data");
            }
        }
        throw new NotFoundException("Error while creating request");
    }

    @Override
    @Transactional
    public Request approveRequest(Worker worker, Request request, Boolean approved) {
        if(approved){
            request.setWorker(worker);
            request.setEndDate(LocalDateTime.now());
            Request savedRequest = requestRepository.save(request);
            try {
                reportService.generatePdfFromHtml(savedRequest);
                return savedRequest;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //approved solution
            //mail notification
            //s3 generate
        } else {
            request.setWorker(worker);
            request.setEndDate(LocalDateTime.now());
            //denied solution
            return null;
        }
    }
}
