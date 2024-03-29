package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.*;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.service.*;
import com.nure.kravchenko.student.reference.service.s3.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    private final StudentService studentService;

    private final RequestService requestService;

    private final ReasonService reasonService;

    private final WorkerService workerService;

    private final StorageService storageService;

    private final SpecialityService specialityService;

    @Autowired
    public WorkerController(StudentService studentService, RequestService requestService, ReasonService reasonService, WorkerService workerService, StorageService storageService, SpecialityService specialityService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.reasonService = reasonService;
        this.workerService = workerService;
        this.storageService = storageService;
        this.specialityService = specialityService;
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
                                                                            @RequestParam boolean approved,
                                                                            @RequestParam String filter) {
        Worker worker = workerService.findWorkerById(id);
        List<WorkerRequestDto> requests = requestService.findAssignedWorkerRequests(worker, approved, filter);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/{id}/requests/nonAssigned")
    public ResponseEntity<List<WorkerRequestDto>> getNonAssignedRequestsByWorkerFaculty(@PathVariable Long id,  @RequestParam String filter) {
        return new ResponseEntity<>(requestService.findWaitingRequest(workerService.findWorkerById(id), filter), HttpStatus.OK);
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
                                                     @RequestParam Boolean approve, @RequestParam String comment,
                                                     @RequestBody byte[] signBytes) throws MessagingException {
        Worker worker = workerService.findWorkerById(workerId);
        Request request = requestService.findById(requestId);

        return new ResponseEntity<>(requestService.approveRequest(worker, request, approve, comment,
                signBytes), HttpStatus.OK);
    }

    @GetMapping("/requests/reasons")
    public ResponseEntity<List<ReasonDto>> getAllRequestReasons() {
        return new ResponseEntity<>(reasonService.getAllReasons(), HttpStatus.OK);
    }

    @GetMapping("/specialities")
    public ResponseEntity<List<SpecialityDto>> getAllSpecialities() {
        return new ResponseEntity<>(specialityService.findAll(), HttpStatus.OK);
    }

}
