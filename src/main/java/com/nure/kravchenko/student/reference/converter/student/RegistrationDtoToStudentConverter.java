package com.nure.kravchenko.student.reference.converter.student;

import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationDtoToStudentConverter implements Converter<RegistrationDto, Student> {

    @Override
    public Student convert(@NonNull RegistrationDto registrationDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return Student.builder()
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
