package com.nure.kravchenko.student.reference.security;

import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.repository.StudentRepository;
import com.nure.kravchenko.student.reference.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    public UserDetailsServiceImpl(StudentRepository studentRepository, WorkerRepository workerRepository) {
        this.studentRepository = studentRepository;
        this.workerRepository = workerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email);
        if (Objects.isNull(student)) {
            Worker worker = workerRepository.findByEmail(email);
            if (Objects.isNull(worker)) {
                throw new UsernameNotFoundException("User doesn't exists");
            } else {
                return SecurityUser.fromWorker(worker);
            }
        } else {
            return SecurityUser.fromStudent(student);
        }
    }

}