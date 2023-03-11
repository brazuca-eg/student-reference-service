package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
