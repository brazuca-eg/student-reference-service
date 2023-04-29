package com.nure.kravchenko.student.reference.service.utils;

import com.nure.kravchenko.student.reference.comparators.*;
import com.nure.kravchenko.student.reference.entity.Request;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

public class FindSortingComparatorUtil {

    private FindSortingComparatorUtil() {
        //private constructor for FindSortingComparatorUtil class.
    }

    public static Comparator<Request> findSortingComparator(String filter) {
        Comparator<Request> comparator = Comparator.comparing(Request::getStartDate).reversed();
        if(StringUtils.isNoneBlank(filter)){
            if (filter.equalsIgnoreCase("reasonNameFilterAsc")) {
                comparator = new ReasonNameAscComparator();
            } else if (filter.equalsIgnoreCase("reasonNameFilterDesc")) {
                comparator = new ReasonNameDescComparator();
            } else if (filter.equalsIgnoreCase("startDateFilterAsc")) {
                comparator = new StartDateAscComparator();
            } else if (filter.equalsIgnoreCase("startDateFilterDesc")) {
                comparator = new StartDateDescComparator();
            } else if (filter.equalsIgnoreCase("studentFilterAsc")) {
                comparator = new StudentEmailAscComparator();
            } else if (filter.equalsIgnoreCase("studentFilterDesc")) {
                comparator = new StudentEmailDescComparator();
            } else if (filter.equalsIgnoreCase("specialityFilterAsc")) {
                comparator = new SpecialityAscComparator();
            } else if (filter.equalsIgnoreCase("specialityFilterDesc")) {
                comparator = new SpecialityDescComparator();
            } else if (filter.equalsIgnoreCase("educationalProgramFilterAsc")) {
                comparator = new EducationalProgramAscComparator();
            } else if (filter.equalsIgnoreCase("educationalProgramFilterDesc")) {
                comparator = new EducationalProgramDescComparator();
            } else if (filter.equalsIgnoreCase("groupFilterAsc")) {
                comparator = new GroupAscComparator();
            } else if (filter.equalsIgnoreCase("groupFilterDesc")) {
                comparator = new GroupDescComparator();
            }else if (filter.equalsIgnoreCase("workerFilterAsc")) {
                comparator = new WorkerEmailAscComparator();
            } else if (filter.equalsIgnoreCase("workerFilterDesc")) {
                comparator = new WorkerEmailDescComparator();
            }
        }
        return comparator;
    }

}
