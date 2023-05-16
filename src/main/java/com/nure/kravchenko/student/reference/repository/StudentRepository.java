package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Student findByEmail(String email);

    @Query(value = "SELECT s FROM Student s WHERE s.approved = false")
    List<Student> findWaitingApprovalStudents();

    @Query(value = "SELECT * from student " +
            "INNER JOIN student_group on student.student_group_id = student_group.id " +
            "where student_group.name = ?1 limit 100", nativeQuery = true)
    List<Student> getStudentsByGroup(String groupName);

}
