package synrgy.finalproject.skyexplorer.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import synrgy.finalproject.skyexplorer.model.provider.AuthProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class UsersDTO {
    private String fistName;
    private String lastName;
    private String password;
    private String salutation;
    private String email;
    private String national;
    private LocalDate dob;
    private String phone;
    private boolean subscribe;
    private String otpCode;
    private LocalDateTime otpExpireTime;
    private boolean isOTPVerified;
    private boolean isRegistrationComplete;
    private String resetPasswordToken;
    private AuthProvider authProvider;
    private String providerId;

    public UsersDTO(String fistName, String lastName, String password, String salutation, String email, String national, LocalDate dob, String phone, boolean subscribe, String otpCode, LocalDateTime otpExpireTime, boolean isOTPVerified, boolean isRegistrationComplete, String resetPasswordToken, AuthProvider authProvider, String providerId) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.password = password;
        this.salutation = salutation;
        this.email = email;
        this.national = national;
        this.dob = dob;
        this.phone = phone;
        this.subscribe = subscribe;
        this.otpCode = otpCode;
        this.otpExpireTime = otpExpireTime;
        this.isOTPVerified = isOTPVerified;
        this.isRegistrationComplete = isRegistrationComplete;
        this.resetPasswordToken = resetPasswordToken;
        this.authProvider = authProvider;
        this.providerId = "testeing";

    }
}
