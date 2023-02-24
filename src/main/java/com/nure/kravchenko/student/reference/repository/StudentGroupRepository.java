package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
    @Query(value = "SELECT * FROM student_group gr WHERE gr.name = ?1 limit 1", nativeQuery = true)
    Optional<StudentGroup> findGroupByName(String name);

//    @Query("SELECT r FROM student_group r WHERE r.name = ?1")
//    Optional<Reason> findReasonByName(String name);
}
