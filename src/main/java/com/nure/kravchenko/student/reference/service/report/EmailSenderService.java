package com.nure.kravchenko.student.reference.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmailAddress;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMailWithAttachment(String toEmail, String body,
                                       String subject, String attachmentPath) throws MessagingException {
        javaMailSender.send(createMessageBaseInfo(toEmail, body, subject, true, attachmentPath));
    }

    public void sendMailWithoutAttachment(String toEmail, String body, String subject) throws MessagingException {
        javaMailSender.send(createMessageBaseInfo(toEmail, body, subject, false, null));
    }

    private MimeMessage createMessageBaseInfo(String toEmail, String body, String subject, boolean isMultipart, String attachmentPath) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        if (isMultipart) {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            FileSystemResource fileSystemResource = new FileSystemResource(new File(attachmentPath));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()),
                    fileSystemResource);
        } else {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
        }
        mimeMessageHelper.setFrom(senderEmailAddress);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);
        return mimeMessage;
    }

}
