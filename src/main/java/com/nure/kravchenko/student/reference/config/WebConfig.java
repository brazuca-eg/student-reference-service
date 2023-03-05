package com.nure.kravchenko.student.reference.config;

import com.nure.kravchenko.student.reference.converter.faculty.FacultyToFacultyDtoConverter;
import com.nure.kravchenko.student.reference.converter.group.StudentGroupToStudentGroupDtoConverter;
import com.nure.kravchenko.student.reference.converter.reason.RequestToRequestDtoConverter;
import com.nure.kravchenko.student.reference.converter.student.RegistrationDtoToStudentConverter;
import com.nure.kravchenko.student.reference.converter.student.StudentToStudentDtoConverter;
import com.nure.kravchenko.student.reference.converter.student.StudentToUserLoggedInDtoConverter;
import com.nure.kravchenko.student.reference.converter.ticket.CreateTicketPayloadToTicketConverter;
import com.nure.kravchenko.student.reference.converter.worker.RegistrationDtoToWorkerConverter;
import com.nure.kravchenko.student.reference.converter.worker.WorkerToUserLoggedInDtoConverter;
import com.nure.kravchenko.student.reference.converter.worker.WorkerToWorkerDtoConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RegistrationDtoToStudentConverter());
        registry.addConverter(new StudentToStudentDtoConverter());
        registry.addConverter(new StudentGroupToStudentGroupDtoConverter());
        registry.addConverter(new CreateTicketPayloadToTicketConverter());
        registry.addConverter(new WorkerToWorkerDtoConverter());
        registry.addConverter(new FacultyToFacultyDtoConverter());
        registry.addConverter(new RequestToRequestDtoConverter());
        registry.addConverter(new RegistrationDtoToWorkerConverter());
        registry.addConverter(new StudentToUserLoggedInDtoConverter());
        registry.addConverter(new WorkerToUserLoggedInDtoConverter());
    }

}
