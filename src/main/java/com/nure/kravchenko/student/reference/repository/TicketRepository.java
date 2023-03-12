package com.nure.kravchenko.student.reference.repository;

import com.nure.kravchenko.student.reference.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.serialNumber = ?1 AND t.number = ?2")
    Optional<Ticket> findBySerialNumberAndNumber(String serialNumber, String number);

}
