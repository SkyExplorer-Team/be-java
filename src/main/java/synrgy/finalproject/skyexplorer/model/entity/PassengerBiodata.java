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
public class PassengerBiodata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    private String name;

    private String address;
}
