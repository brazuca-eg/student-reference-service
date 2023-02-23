package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
