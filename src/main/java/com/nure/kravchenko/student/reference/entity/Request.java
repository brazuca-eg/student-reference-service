package com.nure.kravchenko.student.reference.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"reason", "student", "worker"})
@Builder
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;
    @Column
    private String s3FileName;

    @Column
    private boolean approved;

    @Column
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "reason_id")
    @JsonBackReference
    private Reason reason;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    public void setWorker(Worker worker) {
        worker.getRequests().add(this);
        this.worker = worker;
    }
}
