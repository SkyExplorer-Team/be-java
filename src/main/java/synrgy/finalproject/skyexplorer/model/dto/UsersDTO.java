package synrgy.finalproject.skyexplorer.model.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "First name of the user", example = "John")
    private String fistName;

    @Schema(description = "Last name of the user", example = "Doe")
    @NotBlank(message = "lastName cannot be blank")
    @Size(min = 1, message = "lastName must have at least 1 characters")
    private String lastName;

    @Schema(description = "Last name of the user", example = "Password1234")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @Schema(description = "salutation of the user", example = "Mr.")
    @NotBlank(message = "Salutation cannot be blank")
    @Size(min = 2, message = "Salutation must have at least 2 characters")
    private String salutation;

    @Schema(description = "Email of the user", example = "skyexplorer231@gmail.com")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Schema(description = "National of the user", example = "Jakarta")
    @NotBlank(message = "National cannot be blank")
    private String national;

    @Schema(description = "Date of birth of the user", example = "1990-01-01", format = "date")
    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dob;

    @Schema(description = "Phone of the user", example = "08123456789")
    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 8, message = "Phone must have at least 8 characters")
    @Pattern(regexp="\\+?\\d{8,}", message="Invalid phone number")
    private String phone;
    @AssertTrue(message = "Subscribe must be true")

    @Schema(description = "Boolean value indicating whether the user is subscribed or not", example = "true", type = "boolean")
    private boolean subscribe;
    @Schema(description = "One-time password code", nullable = true)
    @Hidden
    private String otpCode;
    @Schema(description = "One-time password code", nullable = true)
    @Hidden
    private LocalDateTime otpExpireTime;
    @Schema(name = "otpverified", description = "Boolean value indicating whether the user is isOTPVerified or not", example = "false", type = "boolean")
    @Hidden
    private boolean isOTPVerified;
    @Schema(name = "registrationComplete", description = "Boolean value indicating whether the user is isRegistrationComplete or not", example = "false", type = "boolean")
    @Hidden
    private boolean isRegistrationComplete;
    @Schema(description = "One-time resetPasswordToken code", nullable = true)
    @Hidden
    private String resetPasswordToken;
    private AuthProvider authProvider;
    private String providerId;
}
