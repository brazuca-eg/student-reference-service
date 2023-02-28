package com.nure.kravchenko.student.reference.config;

import com.nure.kravchenko.student.reference.converter.group.StudentGroupToStudentGroupDtoConverter;
import com.nure.kravchenko.student.reference.converter.student.CreateStudentRequestToStudentConverter;
import com.nure.kravchenko.student.reference.converter.student.StudentToStudentDtoConverter;
import com.nure.kravchenko.student.reference.converter.ticket.CreateTicketPayloadToTicketConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CreateStudentRequestToStudentConverter());
        registry.addConverter(new StudentToStudentDtoConverter());
        registry.addConverter(new StudentGroupToStudentGroupDtoConverter());
        registry.addConverter(new CreateTicketPayloadToTicketConverter());
    }

}
