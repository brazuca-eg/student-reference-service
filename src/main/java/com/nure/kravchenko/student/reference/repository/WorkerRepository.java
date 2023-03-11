package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    @Query("SELECT w FROM Worker w WHERE w.email = ?1")
    Worker findByEmail(String email);

    @Query(value = "select * from worker w where w.is_admin = false and w.approved = false", nativeQuery = true)
    List<Worker> findWaitingApprovalWorkers();
}
