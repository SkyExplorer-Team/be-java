package synrgy.finalproject.skyexplorer.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import synrgy.finalproject.skyexplorer.model.provider.AuthProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Accessors(chain = true)
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Users extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( updatable = false, nullable = false)
    private UUID id;

    private String firstName;

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

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @JsonIgnoreProperties("user")
    private TravelDocument travelDocument;
}
