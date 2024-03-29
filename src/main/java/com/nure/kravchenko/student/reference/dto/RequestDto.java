package com.nure.kravchenko.student.reference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String reasonName;

    private String reasonDescription;

    private String s3FileName;

    private boolean approved;

    private String comment;

    private String workerFullName;

    private String workerEmail;

    private String workerJobTitle;
}
