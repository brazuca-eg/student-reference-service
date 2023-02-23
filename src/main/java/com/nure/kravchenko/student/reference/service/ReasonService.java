package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.repository.ReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReasonService {
    private final ReasonRepository reasonRepository;

    @Autowired
    public ReasonService(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    public Reason save(Reason reason) {
        return reasonRepository.save(reason);
    }
}
