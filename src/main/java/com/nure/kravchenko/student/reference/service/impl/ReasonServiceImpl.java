package com.nure.kravchenko.student.reference.service.impl;

import com.nure.kravchenko.student.reference.dto.ReasonDto;
import com.nure.kravchenko.student.reference.entity.Reason;
import com.nure.kravchenko.student.reference.repository.ReasonRepository;
import com.nure.kravchenko.student.reference.service.ReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReasonServiceImpl implements ReasonService {
    private final ReasonRepository reasonRepository;

    private final ConversionService conversionService;

    @Autowired
    public ReasonServiceImpl(ReasonRepository reasonRepository, ConversionService conversionService) {
        this.reasonRepository = reasonRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Reason save(Reason reason) {
        return reasonRepository.save(reason);
    }

    @Override
    public List<ReasonDto> getAllReasons() {
        List<Reason> reasons = reasonRepository.findAll();
        return reasons.stream()
                .map(reason -> conversionService.convert(reason, ReasonDto.class))
                .collect(Collectors.toList());
    }

}
