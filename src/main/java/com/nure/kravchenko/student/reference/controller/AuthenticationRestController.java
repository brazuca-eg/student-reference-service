package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.entity.app.Role;
import com.nure.kravchenko.student.reference.payload.AuthenticationRequestDTO;
import com.nure.kravchenko.student.reference.repository.StudentRepository;
import com.nure.kravchenko.student.reference.repository.WorkerRepository;
import com.nure.kravchenko.student.reference.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    private final StudentRepository studentRepository;

    private final WorkerRepository workerRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        StudentRepository studentRepository, WorkerRepository workerRepository,
                                        JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
        this.workerRepository = workerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            String token = "";
            String email = request.getEmail();
            Student student = studentRepository.findByEmail(email);
            if (Objects.isNull(student)) {
                Worker worker = workerRepository.findByEmail(email);
                if (Objects.isNull(worker)) {
                    throw new UsernameNotFoundException("User doesn't exists");
                } else {
                    if (worker.isAdmin()) {
                        token = jwtTokenProvider.createToken(request.getEmail(), Role.ADMIN.name());
                    } else {
                        token = jwtTokenProvider.createToken(request.getEmail(), Role.WORKER.name());
                    }
                }
            } else {
                token = jwtTokenProvider.createToken(request.getEmail(), Role.STUDENT.name());
            }
            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
