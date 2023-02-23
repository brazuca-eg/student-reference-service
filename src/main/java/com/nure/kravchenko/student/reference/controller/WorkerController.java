package com.nure.kravchenko.student.reference.controller;

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

    @GetMapping("/{id}/requests/assigned")
    public ResponseEntity<List<Request>> workerRequests(@PathVariable Long id) {
        Worker worker = workerService.findWorkerById(id);
        List<Request> requests = worker.getRequests();

        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping("/{workerId}/requests/{requestId}/approve")
    public ResponseEntity<Request> approveRequest(@PathVariable Long workerId, @PathVariable Long requestId,
                                                  @RequestParam Boolean approve){
        Worker worker = workerService.findWorkerById(workerId);
        Request request = requestService.findById(requestId);

        return new ResponseEntity<>(requestService.approveRequest(worker, request, approve), HttpStatus.OK);
    }

    //private class ApprovePayload()
}
