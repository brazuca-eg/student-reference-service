package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query("SELECT f FROM Faculty f WHERE f.name = ?1 AND f.shortName = ?2")
    Optional<Faculty> findByNameAndShortName(String name, String shortName);

}
