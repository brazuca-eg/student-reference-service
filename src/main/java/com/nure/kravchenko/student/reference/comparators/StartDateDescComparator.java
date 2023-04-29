package com.nure.kravchenko.student.reference.comparators;

import com.nure.kravchenko.student.reference.entity.Request;

import java.util.Comparator;

public class StartDateDescComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request2.getStartDate().compareTo(request1.getStartDate());
    }
}
