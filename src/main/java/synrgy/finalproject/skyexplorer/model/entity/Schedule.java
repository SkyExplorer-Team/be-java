package synrgy.finalproject.skyexplorer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule {

    @GeneratedValue
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private Airport from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private Airport to;

    private Double price;

    private LocalDateTime timeDeparture;

    private LocalDateTime timeArrive ;

}
