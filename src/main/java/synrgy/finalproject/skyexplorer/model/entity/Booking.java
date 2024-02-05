package synrgy.finalproject.skyexplorer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "schedule_detail_id")
    private ScheduleDetail scheduleDetail;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private LocalDateTime bookingDate;

    private Integer totalcost;

    private String status;
}
