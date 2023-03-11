package com.nure.kravchenko.student.reference.converter.worker;

import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.entity.Worker;
import org.springframework.core.convert.converter.Converter;

public class WorkerToWorkerDtoConverter  implements Converter<Worker, WorkerDto> {

    @Override
    public WorkerDto convert(Worker worker) {
        return WorkerDto.builder()
                .id(worker.getId())
                .name(worker.getName())
                .surname(worker.getSurname())
                .fatherhood(worker.getFatherhood())
                .email(worker.getEmail())
                .isAdmin(worker.isAdmin())
                .approved(worker.isApproved())
                .jobTitle(worker.getJobTitle())
                .gender(worker.getGender())
                .build();
    }

}
