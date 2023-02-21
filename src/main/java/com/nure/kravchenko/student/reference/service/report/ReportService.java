package com.nure.kravchenko.student.reference.service.report;

import com.lowagie.text.pdf.BaseFont;
import com.nure.kravchenko.student.reference.dto.ReportInformation;
import com.nure.kravchenko.student.reference.entity.Faculty;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.StudentGroup;
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
//        context.setVariable(FACULTY, reportInformation.getFaculty());

        return templateEngine.process("templates/thymeleaf_template", context);
    }

    public void generatePdfFromHtml(Request request) throws Exception {
        ReportInformation reportInformation = new ReportInformation();
        Student student = request.getStudent();
        if (student.getApproved()) {
            reportInformation.setFullName(student.getName() + " " + student.getSurname() + " " + student.getFatherhood());
            Character gender = student.getGender();
            if (gender == 'M') {
                reportInformation.setGender("він");
                reportInformation.setStudentGender("студентом");
            } else {
                reportInformation.setGender("вона");
                reportInformation.setStudentGender("студенткою");
            }

            StudentGroup studentGroup = student.getStudentGroup();
            reportInformation.setCourseNumber(String
                    .valueOf(studentGroup.getEndYear().getYear() - studentGroup.getEndYear().getYear()));
            reportInformation.setLearnForm(studentGroup.getLearnForm());
        }


        LocalDate currentDate = LocalDate.now();
        String reportName = student.getName() + "_" + student.getSurname() + "_" + currentDate + ".pdf";
        String outputFolder = "D:\\Java\\Diploma\\Backend\\student-reference-service\\src\\main\\resources\\reports" + reportName;

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
}
