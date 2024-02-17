package synrgy.finalproject.skyexplorer.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TravelDocumentDTO {
    private String passportNumber;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    private String issuingCountryPassport;

    private String NationalIDNumber;

    private String issuingCountryNational;

    private String firstName;

    private String lastName;

    private String phone;
}
