package com.nure.kravchenko.student.reference.service.report;

import com.nure.kravchenko.student.reference.dto.ReportInformation;
import com.nure.kravchenko.student.reference.entity.Student;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Locale;

@Service
public class ReportService {
    private String parseThymeleafTemplate(ReportInformation reportInformation) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);


        Context context = new Context(new Locale("RU"));
        context.setVariable("fullName", reportInformation.getFullName());

        return templateEngine.process("templates/thymeleaf_template", context);
    }

    public void generatePdfFromHtml(Student student) throws Exception {
        ReportInformation reportInformation = new ReportInformation();
        reportInformation.setFullName(student.getName() + " " + student.getSurname() + " " + student.getFatherhood());

        LocalDate currentDate = LocalDate.now();
        String reportName = student.getName() + "_" + student.getSurname() + "_" + currentDate + ".pdf";
        String outputFolder = "D:\\Back\\student-reference-service\\src\\main\\resources\\reports\\" + reportName;

        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();

        renderer.setDocumentFromString(parseThymeleafTemplate(reportInformation));
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }
}
