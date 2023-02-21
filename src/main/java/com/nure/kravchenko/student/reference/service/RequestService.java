package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.CreateRequestPayload;
import com.nure.kravchenko.student.reference.repository.ReasonRepository;
import com.nure.kravchenko.student.reference.repository.RequestRepository;
import com.nure.kravchenko.student.reference.service.report.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
            Request savedRequest = requestRepository.save(request);

            try {
                reportService.generatePdfFromHtml(savedRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return savedRequest;
        }

        throw new NotFoundException("Error while creating request");
    }
}
