package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import com.nure.kravchenko.student.reference.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    private final IStudentService studentService;

    private final IRequestService requestService;

    private final WorkerService workerService;


    @Autowired
    public WorkerController(IStudentService studentService, IRequestService requestService, WorkerService workerService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.workerService = workerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerDto> getWorkerById(@PathVariable Long id) {
        return new ResponseEntity<>(workerService.getWorkerDtoById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<FacultyDto> getWorkerFaculty(@PathVariable Long id) {
        return new ResponseEntity<>(workerService.getWorkerFaculty(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/requests/assigned")
    public ResponseEntity<List<Request>> getAssignedWorkerRequests(@PathVariable Long id) {
        Worker worker = workerService.findWorkerById(id);
        List<Request> requests = worker.getRequests();

        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/{id}/requests/nonAssigned")
    public ResponseEntity<List<RequestDto>> getNonAssignedRequestsByWorkerFaculty(@PathVariable Long id) {
        return new ResponseEntity<>(requestService.findWaitingRequest(workerService.findWorkerById(id)), HttpStatus.OK);
    }

    @PostMapping("/{workerId}/requests/{requestId}/approve")
    public ResponseEntity<Request> approveRequest(@PathVariable Long workerId, @PathVariable Long requestId,
                                                  @RequestParam Boolean approve) {
        Worker worker = workerService.findWorkerById(workerId);
        Request request = requestService.findById(requestId);

        return new ResponseEntity<>(requestService.approveRequest(worker, request, approve), HttpStatus.OK);
    }

}
