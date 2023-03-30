package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "SELECT * FROM request r \n" +
            "INNER JOIN student s ON r.student_id = s.id\n" +
            "INNER JOIN student_group sg ON sg.id = s.student_group_id\n" +
            "INNER JOIN  speciality s2 ON sg.speciality_id = s2.id\n" +
            "INNER JOIN faculty f ON f.id = s2.faculty_id\n" +
            "WHERE r.end_date IS NULL AND faculty_id = ?1", nativeQuery = true)
    List<Request> getWaitingRequestsForFaculty(Long facultyId);


    @Query(value = "SELECT * FROM request r \n" +
            "INNER JOIN student s ON r.student_id = s.id\n" +
            "WHERE r.student_id = ?1", nativeQuery = true)
    List<Request> getRequestByUserId(Long id);

}
