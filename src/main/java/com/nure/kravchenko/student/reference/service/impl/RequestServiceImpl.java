package com.nure.kravchenko.student.reference.service.impl;

import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.WorkerRequestDto;
import com.nure.kravchenko.student.reference.entity.*;
import com.nure.kravchenko.student.reference.exception.InvalidProvidedDataException;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.CreateRequestDto;
import com.nure.kravchenko.student.reference.repository.ReasonRepository;
import com.nure.kravchenko.student.reference.repository.RequestRepository;
import com.nure.kravchenko.student.reference.service.RequestService;
import com.nure.kravchenko.student.reference.service.report.EmailSenderService;
import com.nure.kravchenko.student.reference.service.report.ReportService;
import com.nure.kravchenko.student.reference.service.utils.FindSortingComparatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    private final ReasonRepository reasonRepository;

    private final ReportService reportService;

    private final EmailSenderService emailSenderService;

    private final ConversionService conversionService;

    public RequestServiceImpl(RequestRepository requestRepository, ReasonRepository reasonRepository, ReportService reportService, EmailSenderService emailSenderService, EmailSenderService emailSenderService1, ConversionService conversionService) {
        this.requestRepository = requestRepository;
        this.reasonRepository = reasonRepository;
        this.reportService = reportService;
        this.emailSenderService = emailSenderService1;
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
            if(!student.getStatus().isActive()){
                throw new InvalidProvidedDataException("Особи з неактивним статусом студента не можуть " +
                        "зробити запит на отримання довідки");
            }
            if(LocalDate.now().isAfter(student.getTicket().getEndDate())){
                throw new InvalidProvidedDataException("Ви не можете створити запит на створення довідки через " +
                        "закінчення строку дії студентського квитка");
            }
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
                throw new InvalidProvidedDataException("Ви не пройшли верифікацію для ствоерння запиту");
            }
        }
        throw new NotFoundException("Error while creating request");
    }

    @Override
    public List<WorkerRequestDto> findWaitingRequest(Worker worker, String filter) {
        Faculty faculty = worker.getFaculty();
        if (Objects.nonNull(faculty)) {
            List<Request> requests = requestRepository.getWaitingRequestsForFaculty(faculty.getId());
            Comparator<Request> comparator = FindSortingComparatorUtil.findSortingComparator(filter);

            return requests.stream()
                    .sorted(comparator)
                    .map(request -> conversionService.convert(request, WorkerRequestDto.class))
                    .collect(Collectors.toList());

        }
        throw new NotFoundException("Робітник не належить до жодного деканату");
    }

    @Override
    public List<WorkerRequestDto> findAssignedWorkerRequests(Worker worker, boolean approved, String filter) {
        List<Request> requests = worker.getRequests().stream()
                .filter(request -> Objects.nonNull(request.getEndDate()))
                .filter(request -> request.isApproved() == approved)
                .collect(Collectors.toList());

        Comparator<Request> comparator = FindSortingComparatorUtil.findSortingComparator(filter);

        return requests.stream()
                .sorted(comparator)
                .map(request -> conversionService.convert(request, WorkerRequestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RequestDto approveRequest(Worker worker, Request request, Boolean approved, String comment, byte[] signBytes) {
        if (approved) {
            request.setWorker(worker);
            request.setEndDate(LocalDateTime.now());
            request.setApproved(true);
            request.setComment("Підтверджено");
            Request savedRequest = requestRepository.save(request);
            try {
                String fileName = reportService.generatePdfFromHtml(savedRequest, signBytes);
                request.setS3FileName(fileName);
                return conversionService.convert(savedRequest, RequestDto.class);
            } catch (Exception e) {
                throw new RuntimeException("Problems with generating pdf doc");
            }
        } else {
            request.setApproved(false);
            request.setWorker(worker);
            request.setEndDate(LocalDateTime.now());
            if (StringUtils.isNoneBlank(comment)) {
                request.setComment(comment);
            } else {
                request.setComment("Відмова");
            }
            Request savedRequest = requestRepository.save(request);
            try {
                emailSenderService.sendMailWithoutAttachment("yehor.kravchenko@nure.ua",
                        conversionService.convert(request, String.class), request.getReason().getDescription());
            } catch (MessagingException e) {
                throw new RuntimeException("Problems with sending email");
            }
            return conversionService.convert(savedRequest, RequestDto.class);
        }
    }
}
