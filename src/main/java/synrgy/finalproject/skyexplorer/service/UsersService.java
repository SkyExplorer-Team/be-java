package synrgy.finalproject.skyexplorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.model.dto.UsersDTO;
import synrgy.finalproject.skyexplorer.model.entity.Role;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.repository.RoleRepository;
import synrgy.finalproject.skyexplorer.repository.UsersRepository;

import java.time.LocalDateTime;

@Service
public class UsersService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Users saveUser(UsersDTO usersDTO) {
        if (usersDTO.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        Users existingUser = usersRepository.findByEmail(usersDTO.getEmail());
        if (existingUser != null) {
            if (existingUser.isRegistrationComplete()) {
                // User with this email is already registered and registration is complete
                throw new IllegalArgumentException("User with this email is already registered and registration is complete.");
            }

            Users updatedUser = convertDTOToEntity(usersDTO, null, existingUser.getOtpCode(), existingUser.getOtpExpireTime());
            updatedUser.setId(existingUser.getId());

            updatedUser.setOTPVerified(existingUser.isOTPVerified());
            updatedUser.setRole(existingUser.getRole());
            updatedUser.setRegistrationComplete(existingUser.isRegistrationComplete());

            usersRepository.save(updatedUser);
            return updatedUser;
        }

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = new Role("ROLE_USER");
            roleRepository.save(role);
        }

        String generatedOTP = generateRandomOTP();
        LocalDateTime otpExpireTime = LocalDateTime.now().plusMinutes(5);

        Users newUser = convertDTOToEntity(usersDTO, null, generatedOTP, otpExpireTime);

        newUser.setRole(role);
        newUser.setOTPVerified(false);
        newUser.setRegistrationComplete(false);

        usersRepository.save(newUser);


        return newUser;
    }

    public boolean verifyOTP(Users user, String otpCode) {
        if (user.getOtpCode().equals(otpCode) && LocalDateTime.now().isBefore(user.getOtpExpireTime())) {
            user.setOTPVerified(true);
            usersRepository.save(user);
            return true;
        }
        return false;
    }


    public String sendOTPEmail(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user != null) {
            if (user.getOtpCode() == null || LocalDateTime.now().isAfter(user.getOtpExpireTime())) {
                String generatedOTP = generateRandomOTP();
                user.setOtpCode(generatedOTP);
                user.setOtpExpireTime(LocalDateTime.now().plusMinutes(5));

                String subject = "Verification Code";
                String body = "Your verification code is: " + generatedOTP;
                emailService.sendEmail(user.getEmail(), subject, body);

                usersRepository.save(user);

                return generatedOTP;
            } else {
                // Jika kode OTP masih valid, kirim ulang kode yang sama
                String subject = "Verification Code";
                String body = "Your verification code is: " + user.getOtpCode();
                emailService.sendEmail(user.getEmail(), subject, body);

                return user.getOtpCode();
            }
        }
        return null;
    }

    public String resendOTPEmail(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user != null) {
            String generatedOTP = generateRandomOTP();
            user.setOtpCode(generatedOTP);
            user.setOtpExpireTime(LocalDateTime.now().plusMinutes(5));

            String subject = "Verification Code";
            String body = "Your verification code is: " + generatedOTP;
            emailService.sendEmail(user.getEmail(), subject, body);

            usersRepository.save(user);

            return generatedOTP;
        }
        return null;
    }

    public Users setPassword(String email, String password) {
        Users user = usersRepository.findByEmail(email);
        if (user != null && user.isOTPVerified()) {
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);

            user.setRegistrationComplete(true);
            return usersRepository.save(user);
        }
        return null;
    }

    public boolean verifyOTP(String email, String enteredOTP) {
        Users user = usersRepository.findByEmail(email);

        if (user != null && user.getOtpCode() != null && LocalDateTime.now().isBefore(user.getOtpExpireTime())) {
            return user.getOtpCode().equals(enteredOTP);
        }
        return false;
    }

    private String generateRandomOTP() {
        int length = 5;
        String numbers = "0123456789";
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (numbers.length() * Math.random());
            otp.append(numbers.charAt(index));
        }
        return otp.toString();
    }

    private Users convertDTOToEntity(UsersDTO usersDTO, String password, String otpCode, LocalDateTime otpExpireTime) {
        Users user = new Users(
                usersDTO.getFistName(),
                usersDTO.getLastName(),
                password,
                usersDTO.getSalutation(),
                usersDTO.getEmail(),
                usersDTO.getNational(),
                usersDTO.getDob(),
                usersDTO.getPhone(),
                usersDTO.isSubscribe()
        );

        user.setOtpCode(otpCode);
        user.setOtpExpireTime(otpExpireTime);

        return user;
    }


    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
