package com.capstone.datamate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationCode(String to, String verificationCode) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(to);
            helper.setSubject("Verification Code");

            String htmlContent = "<div style=\"background: linear-gradient(to right, #1AA2A5, #6FCF66); padding: 20px; border-radius: 10px;\">" +
                    "<p style=\"color: white; font-size: 28px;\">Datamate: Streamline Your Data Management</p>" +
                    "<hr style=\"border: 1px solid #ffffff;\">" +
                    "<p style=\" font-size: 20px; margin-bottom: 10px;\">Your verification code is:</p>" +
                    "<p style=\"font-weight: bold; font-size: 24px;\">" + verificationCode + "</p>" +
                    "<p>If you did not request this code, you can ignore this email.</p>" +
                "</div>";
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); 
        }
    }

}
