package com.nure.kravchenko.student.reference.comparators;

import com.nure.kravchenko.student.reference.entity.Request;

import java.util.Comparator;

public class GroupDescComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request2.getStudent().getStudentGroup().getName()
                .compareTo(request1.getStudent().getStudentGroup().getName());
    }
}
