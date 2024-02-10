package synrgy.finalproject.skyexplorer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDetailDto {
    private UUID scheduleId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureAirport;
    private String departureCity;
    private String departureCountry;
    private String arrivalAirport;
    private String arrivalCity;
    private String arrivalCountry;
    private String airplaneCode;
    private String airplaneName;
    private String cabinClass;
    private String ticketType;
    private String cabinBaggage;
    private String checkedBaggage;
    private String seatSelection;
    private boolean baggageDelayProtection;
    private boolean mealPack;
    private String cancellation;
    private String flightChange;
    private String noShow;
    private BigDecimal price;
}
