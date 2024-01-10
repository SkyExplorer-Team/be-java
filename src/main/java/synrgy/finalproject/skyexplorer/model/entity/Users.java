package synrgy.finalproject.skyexplorer.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Users extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fistName;

    @NotBlank(message = "lastName cannot be blank")
    @Size(min = 1, message = "lastName must have at least 1 characters")
    private String lastName;

    @Size(min = 8, message = "Username must have at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must contain at least one letter and one digit")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public Users(String fistName, String lastName, String password, String salutation, String email, String national, LocalDate dob, String phone, boolean subscribe) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.password = password;
        this.salutation = salutation;
        this.email = email;
        this.national = national;
        this.dob = dob;
        this.phone = phone;
        this.subscribe = subscribe;
    }
}
