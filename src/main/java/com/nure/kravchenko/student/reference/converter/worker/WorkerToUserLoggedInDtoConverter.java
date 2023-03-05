package com.nure.kravchenko.student.reference.converter.worker;

import com.nure.kravchenko.student.reference.dto.UserLoggedInDto;
import com.nure.kravchenko.student.reference.entity.Worker;
import org.springframework.core.convert.converter.Converter;

public class WorkerToUserLoggedInDtoConverter implements Converter<Worker, UserLoggedInDto> {

    @Override
    public UserLoggedInDto convert(Worker worker) {
        return UserLoggedInDto.builder()
                .id(worker.getId())
                .email(worker.getEmail())
                .name(worker.getName())
                .surname(worker.getSurname())
                .fatherhood(worker.getFatherhood())
                .gender(worker.getGender())
                .approved(worker.isApproved())
                .build();
    }

}
