package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    private final ConversionService conversionService;

    @Autowired
    public WorkerService(WorkerRepository workerRepository, ConversionService conversionService) {
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

    public WorkerDto getWorkerDtoById(Long id){
        Worker worker = findWorkerById(id);
        return conversionService.convert(worker, WorkerDto.class);
    }

    public FacultyDto getWorkerFaculty(Long id){
        Worker worker = findWorkerById(id);
        Faculty faculty = worker.getFaculty();
        if(Objects.nonNull(faculty)){
            return conversionService.convert(faculty, FacultyDto.class);
        }
        throw new NotFoundException("The worker doesn't have faculty");
    }

}
