package synrgy.finalproject.skyexplorer.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "travelDocument")
public class TravelDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( updatable = false, nullable = false)
    private UUID id;

    private String passportNumber;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    private String issuingCountryPassport;

    private String NationalIDNumber;

    private String issuingCountryNational;

    private String firstName;

    private String lastName;

    private String phone;

    @JsonBackReference
    @OneToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id")
    private Users user;
}
