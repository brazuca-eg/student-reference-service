package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.dto.StudentDto;
import com.nure.kravchenko.student.reference.dto.UserLoggedInDto;
import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.entity.app.Role;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import com.nure.kravchenko.student.reference.exception.NureEmailException;
import com.nure.kravchenko.student.reference.payload.AuthenticationRequestDto;
import com.nure.kravchenko.student.reference.payload.RegistrationDto;
import com.nure.kravchenko.student.reference.security.JwtTokenProvider;
import com.nure.kravchenko.student.reference.service.StudentService;
import com.nure.kravchenko.student.reference.service.WorkerService;
import com.nure.kravchenko.student.reference.service.utils.ValidationUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final StudentService studentService;

    private final WorkerService workerService;

    private final JwtTokenProvider jwtTokenProvider;

    private final ConversionService conversionService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, StudentService studentService,
                                    WorkerService workerService, JwtTokenProvider jwtTokenProvider,
                                    ConversionService conversionService) {
        this.authenticationManager = authenticationManager;
        this.studentService = studentService;
        this.workerService = workerService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.conversionService = conversionService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoggedInDto> authenticate(@RequestBody @Valid AuthenticationRequestDto request) {
        String email = request.getEmail();
        if (BooleanUtils.isFalse(ValidationUtils.isValidEmailAddress(email))) {
            throw new NureEmailException("Дана email адреса не задовольняє правилам поштового акаунту ХНУРЕ");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token;
            String role;
            Worker worker = null;
            Student student = studentService.findByEmail(email);
            if (Objects.isNull(student)) {
                worker = workerService.findByEmail(email);
                if (Objects.isNull(worker)) {
                    throw new NotFoundException("Немає користувача з такими даними в системі");
                } else {
                    if (worker.isAdmin()) {
                        role = Role.ADMIN.name();
                        token = jwtTokenProvider.createToken(request.getEmail(), role);
                    } else {
                        role = Role.WORKER.name();
                        token = jwtTokenProvider.createToken(request.getEmail(), role);
                    }
                }
            } else {
                role = Role.STUDENT.name();
                token = jwtTokenProvider.createToken(request.getEmail(), role);
            }
            UserLoggedInDto resultDto;
            if (Objects.nonNull(student)) {
                resultDto = conversionService.convert(student, UserLoggedInDto.class);
            } else {
                resultDto = conversionService.convert(worker, UserLoggedInDto.class);
            }
            resultDto.setToken(token);
            resultDto.setRole(role);
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        } catch (AuthenticationException e) {
            // TODO: 05.03.2023 Add forbidden exc
            throw new NotFoundException("Немає користувача з такими даними в системі");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegistrationDto registrationDto) {
        if (BooleanUtils.isFalse(ValidationUtils.isValidEmailAddress(registrationDto.getEmail()))) {
            throw new NureEmailException("Дана email адреса не задовольняє правилам поштового акаунту ХНУРЕ");
        }
        if (StringUtils.equalsIgnoreCase(Role.WORKER.name(), registrationDto.getRole())) {
            WorkerDto workerDto = workerService.create(registrationDto);
            return new ResponseEntity<>(Objects.nonNull(workerDto), HttpStatus.CREATED);
        } else if (StringUtils.equalsIgnoreCase(Role.STUDENT.name(), registrationDto.getRole())) {
            StudentDto studentDto = studentService.create(registrationDto);
            return new ResponseEntity<>(Objects.nonNull(studentDto), HttpStatus.CREATED);
        }
        throw new NotFoundException("Немає такої ролі у системі");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
