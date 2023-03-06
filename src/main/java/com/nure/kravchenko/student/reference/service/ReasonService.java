package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.ReasonDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.repository.ReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReasonService {
    private final ReasonRepository reasonRepository;

    private final ConversionService conversionService;

    @Autowired
    public ReasonService(ReasonRepository reasonRepository, ConversionService conversionService) {
        this.reasonRepository = reasonRepository;
        this.conversionService = conversionService;
    }

    public Reason save(Reason reason) {
        return reasonRepository.save(reason);
    }

    public List<ReasonDto> getAllReasons() {
        List<Reason> reasons = reasonRepository.findAll();
        return reasons.stream()
                .map(reason -> conversionService.convert(reason, ReasonDto.class))
                .collect(Collectors.toList());
    }

}
