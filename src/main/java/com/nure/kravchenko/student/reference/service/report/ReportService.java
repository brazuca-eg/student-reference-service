package com.nure.kravchenko.student.reference.service.report;

import com.lowagie.text.pdf.BaseFont;
import com.nure.kravchenko.student.reference.dto.ReportInformation;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.service.s3.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Locale;

@Service
public class ReportService {

    private static final String FULL_NAME = "fullName";

    private static final String GENDER = "gender";

    private static final String STUDENT_GENDER = "studentGender";

    private static final String COURSE_NUMBER = "courseNumber";

    private static final String LEARN_FORM = "learnForm";

    private static final String FACULTY = "faculty";

    private static final String DEGREE_FORM = "degreeForm";

    private static final String START_DATE = "startDate";

    private static final String END_DATE = "endDate";

    private static final String REASON = "reason";

    private static final String REPORT_DATE = "reportDate";

    private final StorageService storageService;

    private final EmailSenderService emailSenderService;

    private final ConversionService conversionService;

    @Autowired
    public ReportService(StorageService storageService, EmailSenderService emailSenderService, ConversionService conversionService) {
        this.storageService = storageService;
        this.emailSenderService = emailSenderService;
        this.conversionService = conversionService;
    }

    private String parseThymeleafTemplate(ReportInformation reportInformation) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context(new Locale("RU"));
        context.setVariable(FULL_NAME, reportInformation.getFullName());
        context.setVariable(GENDER, reportInformation.getGender());
        context.setVariable(STUDENT_GENDER, reportInformation.getStudentGender());
        context.setVariable(COURSE_NUMBER, reportInformation.getCourseNumber());
        context.setVariable(LEARN_FORM, reportInformation.getLearnForm());
        context.setVariable(FACULTY, reportInformation.getFaculty());
        context.setVariable(DEGREE_FORM, reportInformation.getDegreeForm());
        context.setVariable(REASON, reportInformation.getReason());
        context.setVariable(START_DATE, reportInformation.getStartDate());
        context.setVariable(END_DATE, reportInformation.getEndDate());
        context.setVariable(REPORT_DATE, reportInformation.getReportDate());

        return templateEngine.process("templates/thymeleaf_template", context);
    }

    @Transactional
    public String generatePdfFromHtml(Request request) throws Exception {
        Student student = request.getStudent();
        if (student.isApproved()) {
            ReportInformation reportInformation = conversionService.convert(request, ReportInformation.class);
            String directory = new File("./").getAbsolutePath();
            String path = directory.substring(0, directory.length() - 1) + "src\\main\\resources\\reports\\";
            LocalDate currentDate = LocalDate.now();
            String reportName = student.getName() + "_" + student.getSurname() + "_" + currentDate + ".pdf";
            String outputFolder = path + reportName;

            OutputStream outputStream = new FileOutputStream(outputFolder);

            ClassLoader classLoader = getClass().getClassLoader();
            File fontFile = new File(classLoader.getResource("static/verdana.ttf").getFile());

            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont(fontFile.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(parseThymeleafTemplate(reportInformation));
            renderer.layout();
            renderer.createPDF(outputStream);

            outputStream.close();

            emailSenderService.sendMailWithAttachment("yehor.kravchenko@nure.ua",
                    request.getReason().getDescription(), request.getReason().getDescription(), outputFolder);

            File created = new File(outputFolder);
            //storageService.uploadFile(created);
            String fileName = created.getName();
            created.delete();
            return fileName;
        }
        throw new RuntimeException("Student not approved exception");
    }
}
