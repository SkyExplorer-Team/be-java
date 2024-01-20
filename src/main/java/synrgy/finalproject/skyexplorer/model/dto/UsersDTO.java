package synrgy.finalproject.skyexplorer.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.Accessors;
import synrgy.finalproject.skyexplorer.model.provider.AuthProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UsersDTO {

    private String fistName;

    @NotBlank(message = "lastName cannot be blank")
    @Size(min = 1, message = "lastName must have at least 1 characters")
    private String lastName;

    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;


    @NotBlank(message = "Salutation cannot be blank")
    @Size(min = 2, message = "Salutation must have at least 2 characters")
    private String salutation;


    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "National cannot be blank")
    private String national;

    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dob;

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 8, message = "Phone must have at least 8 characters")
    @Pattern(regexp="\\+?\\d{8,}", message="Invalid phone number")
    private String phone;
    @AssertTrue(message = "Subscribe must be true")

    private boolean subscribe;
    private String otpCode;
    private LocalDateTime otpExpireTime;
    private boolean isOTPVerified;
    private boolean isRegistrationComplete;
    private String resetPasswordToken;
    private AuthProvider authProvider;
    private String providerId;
}
