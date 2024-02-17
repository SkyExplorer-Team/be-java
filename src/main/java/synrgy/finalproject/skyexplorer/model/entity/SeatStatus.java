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
public class SeatStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "seat_selection_id")
    private SeatSelection seatSelection;

    private boolean bokked;
}
