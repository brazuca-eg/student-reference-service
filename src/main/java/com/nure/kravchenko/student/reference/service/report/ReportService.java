package com.nure.kravchenko.student.reference.service.report;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.nure.kravchenko.student.reference.dto.ReportInformation;
import com.nure.kravchenko.student.reference.entity.Request;
import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.exception.InvalidProvidedDataException;
import com.nure.kravchenko.student.reference.service.s3.StorageService;
import com.nure.kravchenko.student.reference.service.utils.RandomUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;

import static com.nure.kravchenko.student.reference.service.utils.ProjectConstants.UNDERSCORE;
import static com.nure.kravchenko.student.reference.service.utils.ReportConstants.*;

@Log4j
@Service
public class ReportService {

    private static final int MIN = 100;

    private static final int MAX = 1000;

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
        templateResolver.setSuffix(HTML_EXTENSION);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(REPORT_ENCODING);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context(new Locale(REPORT_LANGUAGE));
        context.setVariable(FULL_NAME, reportInformation.getFullName());
        context.setVariable(GENDER, reportInformation.getGender());
        context.setVariable(STUDENT_GENDER, reportInformation.getStudentGender());
        context.setVariable(COURSE_NUMBER, reportInformation.getCourseNumber());
        context.setVariable(LEARN_FORM, reportInformation.getLearnForm());
        context.setVariable(FACULTY, reportInformation.getFaculty());
        context.setVariable(SPECIALITY, reportInformation.getSpeciality());
        context.setVariable(EDUCATIONAL_PROGRAM, reportInformation.getEducationalProgram());
        context.setVariable(DEGREE_FORM, reportInformation.getDegreeForm());
        context.setVariable(REASON, reportInformation.getReason());
        context.setVariable(START_DATE, reportInformation.getStartDate());
        context.setVariable(END_DATE, reportInformation.getEndDate());
        context.setVariable(REPORT_DATE, reportInformation.getReportDate());
        context.setVariable(WORKER_INFO, reportInformation.getWorkerInfo());
        context.setVariable(IMAGE, reportInformation.getSign());
        return templateEngine.process("templates/thymeleaf_template", context);
    }

    @Transactional
    public String generatePdfFromHtml(Request request, byte[] signBytes) {
        log.info("Starting generatePdf");
        Student student = request.getStudent();
        if (student.isApproved()) {
            ReportInformation reportInformation = conversionService.convert(request, ReportInformation.class);

            String directory = new File("./").getAbsolutePath();
            String signImagePath = directory.substring(0, directory.length() - 1)
                    + "src\\main\\resources\\templates\\";
            ByteArrayInputStream bis = new ByteArrayInputStream(signBytes);
            BufferedImage bImage2 = null;
            try {
                bImage2 = ImageIO.read(bis);
                String signImage = signImagePath + "sign" + "." + JPG_EXTENSION;
                ImageIO.write(bImage2, JPG_EXTENSION, new File(signImage));
                File createdSignImage = new File(signImage);
                String base64ImageCode = Base64.getEncoder()
                        .encodeToString(Files.readAllBytes(createdSignImage.toPath()));
                assert reportInformation != null;
                reportInformation.setSign(base64ImageCode);
                createdSignImage.delete();
            } catch (IOException e) {
                throw new RuntimeException("The image can't be read");
            }

            String path = directory.substring(0, directory.length() - 1) + "src\\main\\resources\\reports\\";
            LocalDate currentDate = LocalDate.now();
            String reportName = student.getSurname() + UNDERSCORE + student.getName() + UNDERSCORE +
                    student.getFatherhood() + UNDERSCORE + currentDate + UNDERSCORE +
                    RandomUtils.getRandomNumber(MIN, MAX) + PDF_EXTENSION;
            String outputFolder = path + reportName;

            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(outputFolder);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("119");
            }

            ClassLoader classLoader = getClass().getClassLoader();
            File fontFile = new File(Objects.requireNonNull(classLoader
                    .getResource(REPORT_FONT)).getFile());

            ITextRenderer renderer = new ITextRenderer();
            try {
                renderer.getFontResolver().addFont(fontFile.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            } catch (DocumentException e) {
                throw new RuntimeException("131 ex");
            } catch (IOException e) {
                throw new RuntimeException("133 ex");
            }
            renderer.setDocumentFromString(parseThymeleafTemplate(reportInformation));
            renderer.layout();
            try {
                renderer.createPDF(outputStream);
            } catch (DocumentException e) {
                throw new RuntimeException("140 ex");
            }

            try {
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("146 ex");
            }

            try {
                emailSenderService.sendMailWithAttachment("yehor.kravchenko@nure.ua",
                        conversionService.convert(request, String.class),
                        request.getReason().getDescription(), outputFolder);
            } catch (MessagingException e) {
                throw new RuntimeException("155");
            }

            File created = new File(outputFolder);
            storageService.uploadFile(created);
            String fileName = created.getName();
            created.delete();
            return fileName;
        }
        throw new InvalidProvidedDataException("Студентам з непідтвердженним акаунтом не дозволено генерувати довідки");
    }
}
