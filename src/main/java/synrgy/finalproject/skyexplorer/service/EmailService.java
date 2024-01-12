package synrgy.finalproject.skyexplorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("creativesoul290@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendResetPasswordEmail(String to, String resetPasswordToken) {
        String subject = "Reset Password";
        String body = "Click the link below to reset your password:\n"
                + "http://localhost:8080/api/users/reset-password?token=" + resetPasswordToken;

        sendEmail(to, subject, body);
    }
}
