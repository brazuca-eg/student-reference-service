package com.nure.kravchenko.student.reference.converter.worker;

import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationDtoToWorkerConverter implements Converter<RegistrationDto, Worker> {

    @Override
    public Worker convert(@NonNull RegistrationDto registrationDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return Worker.builder()
                .name(registrationDto.getName())
                .surname(registrationDto.getSurname())
                .fatherhood(registrationDto.getFatherhood())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .gender(registrationDto.getGender())
                .approved(false)
                .build();
    }

}
