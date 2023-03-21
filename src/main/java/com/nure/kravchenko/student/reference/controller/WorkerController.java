package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.dto.RequestDto;
import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.dto.WorkerRequestDto;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.service.IRequestService;
import com.nure.kravchenko.student.reference.service.IStudentService;
import com.nure.kravchenko.student.reference.service.WorkerService;
import com.nure.kravchenko.student.reference.service.s3.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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

    private final StorageService storageService;

    @Autowired
    public WorkerController(IStudentService studentService, IRequestService requestService, WorkerService workerService, StorageService storageService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.workerService = workerService;
        this.storageService = storageService;
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
    public ResponseEntity<List<WorkerRequestDto>> getAssignedWorkerRequests(@PathVariable Long id,
                                                                            @RequestParam boolean approved) {
        Worker worker = workerService.findWorkerById(id);
        List<WorkerRequestDto> requests = requestService.findAssignedWorkerRequests(worker, approved);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/{id}/requests/nonAssigned")
    public ResponseEntity<List<WorkerRequestDto>> getNonAssignedRequestsByWorkerFaculty(@PathVariable Long id) {
        return new ResponseEntity<>(requestService.findWaitingRequest(workerService.findWorkerById(id)), HttpStatus.OK);
    }

    @GetMapping("/requests/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadReport(@PathVariable String fileName) {
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/pdf")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }


    @PostMapping("/{workerId}/requests/{requestId}")
    public ResponseEntity<RequestDto> approveRequest(@PathVariable Long workerId, @PathVariable Long requestId,
                                                     @RequestParam Boolean approve, @RequestParam String comment) {
        Worker worker = workerService.findWorkerById(workerId);
        Request request = requestService.findById(requestId);

        return new ResponseEntity<>(requestService.approveRequest(worker, request, approve, comment), HttpStatus.OK);
    }

}
