package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Reason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReasonRepository extends JpaRepository<Reason, Long> {

    @Query("SELECT r FROM Reason r WHERE r.name = ?1")
    List<Reason> findReasonByName(String name);


}
