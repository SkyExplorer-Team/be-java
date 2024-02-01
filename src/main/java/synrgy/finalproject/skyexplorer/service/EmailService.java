package synrgy.finalproject.skyexplorer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.core.task.TaskExecutor;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Qualifier("taskExecutor")
    private final TaskExecutor taskExecutor;

    @Value("${spring.mail.username}")
    private String emailSender;

    public void sendAsync(String to, String subject, String body){
        taskExecutor.execute(() -> sendEmail(to, subject, body));
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendResetPasswordEmail(String to, String resetPasswordToken) {
        String subject = "Reset Password";
        String body = "Click the link below to reset your password:\n"
                + "https://be-java-production.up.railway.app/api/auth/reset-password?token=" + resetPasswordToken;

        sendAsync(to, subject, body);
    }
}
