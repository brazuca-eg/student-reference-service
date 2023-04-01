package com.nure.kravchenko.student.reference.service.impl;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.payload.admin.ApproveWorkerDto;
import com.nure.kravchenko.student.reference.repository.WorkerRepository;
import com.nure.kravchenko.student.reference.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    private final ConversionService conversionService;

    @Autowired
    public WorkerServiceImpl(WorkerRepository workerRepository, ConversionService conversionService) {
        this.workerRepository = workerRepository;
        this.conversionService = conversionService;
    }

    public Worker findWorkerById(Long id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            return optionalWorker.get();
        }
        throw new NotFoundException("There are problems with worker id");
    }

    public Worker findByEmail(String email) {
        Worker worker = workerRepository.findByEmail(email);
        if (Objects.nonNull(worker)) {
            return worker;
        }
        return null;
    }

    public WorkerDto getWorkerDtoById(Long id) {
        Worker worker = findWorkerById(id);
        return conversionService.convert(worker, WorkerDto.class);
    }

    public List<WorkerDto> getWaitingApproveWorkers() {
        List<Worker> workers = workerRepository.findWaitingApprovalWorkers();
        return  workers.stream()
                .map(worker -> conversionService.convert(worker, WorkerDto.class))
                .collect(Collectors.toList());
    }

    public WorkerDto approveWorker(Long id, ApproveWorkerDto approveWorkerDto, Faculty faculty) {
        Worker worker = findWorkerById(id);
        worker.setJobTitle(approveWorkerDto.getJobTitle());
        worker.setFaculty(faculty);
        worker.setApproved(true);
        Worker updated = save(worker);
        return conversionService.convert(updated, WorkerDto.class);
    }

    public FacultyDto getWorkerFaculty(Long id) {
        Worker worker = findWorkerById(id);
        Faculty faculty = worker.getFaculty();
        if (Objects.nonNull(faculty)) {
            return conversionService.convert(faculty, FacultyDto.class);
        }
        throw new NotFoundException("The worker doesn't have faculty");
    }

    public WorkerDto create(RegistrationDto registrationDto) {
        Worker worker = conversionService.convert(registrationDto, Worker.class);

        assert worker != null;
        Worker created = workerRepository.save(worker);

        return conversionService.convert(created, WorkerDto.class);
    }

    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }

}
