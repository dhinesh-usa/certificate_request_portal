package com.examly.springapp.service;

// NotificationService is disabled - no SMTP server configured
// To enable: configure spring.mail.* in application.properties and uncomment this class

/*
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    public void sendApprovalEmail(String toEmail, String studentName, String courseName, String certificateUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toEmail);
        message.setSubject("Your Certificate Request Has Been Approved");
        message.setText(
            "Dear " + studentName + ",\n\n" +
            "Your certificate request for the course \"" + courseName + "\" has been approved.\n\n" +
            "You can download your certificate here:\n" + certificateUrl + "\n\n" +
            "Regards,\nCertificate Request System"
        );
        mailSender.send(message);
    }

    public void sendRejectionEmail(String toEmail, String studentName, String courseName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toEmail);
        message.setSubject("Your Certificate Request Has Been Rejected");
        message.setText(
            "Dear " + studentName + ",\n\n" +
            "Unfortunately, your certificate request for the course \"" + courseName + "\" has been rejected.\n\n" +
            "Please contact your administrator for more information.\n\n" +
            "Regards,\nCertificate Request System"
        );
        mailSender.send(message);
    }
}
*/
