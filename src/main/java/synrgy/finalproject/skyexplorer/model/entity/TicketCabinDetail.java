package synrgy.finalproject.skyexplorer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TicketCabinDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "cabin_class_id")
    private CabinClass cabinClass;

    private String cabinbaggage;

    private String checkedbaggage;

    private String seatselection;

    private boolean baggagedelayprotection;

    private boolean mealpack;

    private String cancellation;

    private String flightchange;

    private String noshow;
}
