package com.nure.kravchenko.student.reference.service.report;

import com.lowagie.text.pdf.BaseFont;
import com.nure.kravchenko.student.reference.dto.ReportInformation;
import com.nure.kravchenko.student.reference.entity.*;
import com.nure.kravchenko.student.reference.service.s3.StorageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

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

    @Autowired
    private final StorageService storageService;

    public ReportService(StorageService storageService) {
        this.storageService = storageService;
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
        context.setVariable(START_DATE, reportInformation.getStartDate());
        context.setVariable(END_DATE, reportInformation.getEndDate());
        context.setVariable(REPORT_DATE, reportInformation.getReportDate());

        return templateEngine.process("templates/thymeleaf_template", context);
    }

    public void generatePdfFromHtml(Request request) throws Exception {
        ReportInformation reportInformation = new ReportInformation();
        Student student = request.getStudent();
        if (student.isApproved()) {
            // TODO: 25.02.2023 Implement Converter
            reportInformation.setFullName(student.getName() + " " + student.getSurname() + " " + student.getFatherhood());
            Character gender = student.getGender();
            if (Objects.nonNull(gender) && gender == 'M') {
                reportInformation.setGender("він");
                reportInformation.setStudentGender("студентом");
            } else {
                reportInformation.setGender("вона");
                reportInformation.setStudentGender("студенткою");
            }

            StudentGroup studentGroup = student.getStudentGroup();
            reportInformation.setCourseNumber(String
                    .valueOf(studentGroup.getEndYear().getYear() - studentGroup.getEndYear().getYear()));
            String learnForm = studentGroup.getLearnForm();
            if (StringUtils.equalsIgnoreCase("Денна", learnForm)) {
                reportInformation.setLearnForm("денної");
            } else {
                reportInformation.setLearnForm("заочної");
            }
            reportInformation.setDegreeForm(studentGroup.getDegreeForm());
            reportInformation.setStartDate(studentGroup.getStartYear().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            reportInformation.setEndDate(studentGroup.getEndYear().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            reportInformation.setReportDate(request.getEndDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            Speciality speciality = studentGroup.getSpeciality();
            Faculty faculty = speciality.getFaculty();
            reportInformation.setFaculty(faculty.getName());


            LocalDate currentDate = LocalDate.now();
            String reportName = student.getName() + "_" + student.getSurname() + "_" + currentDate + ".pdf";
            String outputFolder = "D:\\Java\\Diploma\\Backend\\student-reference-service\\src\\main\\resources\\reports\\" + reportName;

            OutputStream outputStream = new FileOutputStream(outputFolder);

            ClassLoader classLoader = getClass().getClassLoader();
            File fontFile = new File(classLoader.getResource("static/verdana.ttf").getFile());

            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont(fontFile.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(parseThymeleafTemplate(reportInformation));
            renderer.layout();
            renderer.createPDF(outputStream);

            outputStream.close();
        }


        //S3
//        File file =  new File("D:\\Java\\Diploma\\Backend\\student-reference-service\\src\\main\\resources\\reports\\Аліна_Мільник_2023-02-21.pdf");
//        if(file.exists()){
//            storageService.uploadFile(file);
//        }
    }
}
