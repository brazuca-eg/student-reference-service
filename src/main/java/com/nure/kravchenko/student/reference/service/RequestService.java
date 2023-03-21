package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.WorkerRequestDto;
import com.nure.kravchenko.student.reference.entity.*;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.CreateRequestDto;
import com.nure.kravchenko.student.reference.repository.ReasonRepository;
import com.nure.kravchenko.student.reference.repository.RequestRepository;
import com.nure.kravchenko.student.reference.service.report.EmailSenderService;
import com.nure.kravchenko.student.reference.service.report.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestService implements IRequestService {

    private final RequestRepository requestRepository;

    private final ReasonRepository reasonRepository;

    private final ReportService reportService;

    private final ConversionService conversionService;

    public RequestService(RequestRepository requestRepository, ReasonRepository reasonRepository, ReportService reportService, EmailSenderService emailSenderService, ConversionService conversionService) {
        this.requestRepository = requestRepository;
        this.reasonRepository = reasonRepository;
        this.reportService = reportService;
        this.conversionService = conversionService;
    }

    @Override
    public Request findById(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            return optionalRequest.get();
        }
        throw new NotFoundException("There are problems with request id");
    }

    @Override
    public RequestDto createRequest(Student student, CreateRequestDto requestPayload) {
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
                    Request created = requestRepository.save(request);
                    return conversionService.convert(created, RequestDto.class);
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
    public List<WorkerRequestDto> findWaitingRequest(Worker worker) {
        Faculty faculty = worker.getFaculty();
        if (Objects.nonNull(faculty)) {
            List<Request> requests = requestRepository.getWaitingRequestsForFaculty(faculty.getId());
            return requests.stream()
                    .map(request -> conversionService.convert(request, WorkerRequestDto.class))
                    .collect(Collectors.toList());
        }
        throw new NotFoundException("There worker doesn't have a faculty");
    }

    @Override
    public List<WorkerRequestDto> findAssignedWorkerRequests(Worker worker) {
        return worker.getRequests()
                .stream()
                .filter(request -> Objects.nonNull(request.getEndDate()))
                .map(request -> conversionService.convert(request, WorkerRequestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RequestDto approveRequest(Worker worker, Request request, Boolean approved, String comment) {
        if (approved) {
            request.setWorker(worker);
            request.setEndDate(LocalDateTime.now());
            request.setApproved(true);
            request.setComment("Approved");
            Request savedRequest = requestRepository.save(request);
            try {
                String fileName = reportService.generatePdfFromHtml(savedRequest);
                request.setS3FileName(fileName);
                return conversionService.convert(savedRequest, RequestDto.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            request.setApproved(false);
            request.setWorker(worker);
            request.setEndDate(LocalDateTime.now());
            if (StringUtils.isNoneBlank(comment)) {
                request.setComment(comment);
            } else {
                request.setComment("Default denied");
            }
            Request savedRequest = requestRepository.save(request);
            return conversionService.convert(savedRequest, RequestDto.class);
        }
    }
}
