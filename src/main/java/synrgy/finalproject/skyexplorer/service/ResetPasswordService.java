package synrgy.finalproject.skyexplorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.exception.UsersNotFoundException;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.repository.UsersRepository;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ResetPasswordService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsersRepository usersRepository;

    public void updateResetPassword(String email) throws UsersNotFoundException {
        Users users = usersRepository.findByEmail(email);

        if (users != null) {
            String resetPasswordToken = generateResetPasswordToken();
            users.setResetPasswordToken(resetPasswordToken);
            usersRepository.save(users);

            // Kirim email reset password
            emailService.sendResetPasswordEmail(users.getEmail(), resetPasswordToken);
        } else {
            throw new UsersNotFoundException("Tidak dapat menemukan pengguna dengan email " + email);
        }
    }

    public Users get(String resetPasswordToken) {
        return usersRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(Users users, String newPassword) {
        System.out.println("Updating password for user: " + users.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println("Password to be validated: " + newPassword);

        String encodedPassword = passwordEncoder.encode(newPassword);

        users.setPassword(encodedPassword);
        users.setResetPasswordToken(null);

        usersRepository.save(users);
        System.out.println("Password updated successfully.");
    }

    public String generateResetPasswordToken() {
        return UUID.randomUUID().toString();
    }

}
