package com.nure.kravchenko.student.reference.service;

import com.nure.kravchenko.student.reference.dto.ReasonDto;
import com.nure.kravchenko.student.reference.entity.Reason;

import java.util.List;

public interface ReasonService {

    Reason save(Reason reason);

    List<ReasonDto> getAllReasons();

}
