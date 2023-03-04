package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    @Query("SELECT w FROM Worker w WHERE w.email = ?1")
    Worker findByEmail(String email);

}
