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

//    @Query(value = "select * from student_group gr inner join student st on gr.id = st.student_group_id where st.id = :studentId ", nativeQuery = true)
//    StudentGroup findGroupByStudentId(Long studentId);

//    @Query(value = "INSERT INTO student(ticket_id) values(?1)", nativeQuery = true)
//    Ticket addTicket(Long ticketId);

}
