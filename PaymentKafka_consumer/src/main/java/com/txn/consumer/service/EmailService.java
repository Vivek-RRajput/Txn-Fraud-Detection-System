package com.txn.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("vrrajput1720@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending mail: " + e.getMessage());
        }
    }
    
    public void sendHtmlMail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("vrrajput1720@gmail.com"); // must match spring.mail.username
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = enable HTML

            mailSender.send(message);
            System.out.println("Mail sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Error sending HTML mail: " + e.getMessage());
        }
    }
}
