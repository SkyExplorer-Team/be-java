package synrgy.finalproject.skyexplorer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @JsonIgnore
    @ManyToOne
    private National national;
    @NotNull
    private String name;
    @NotNull
    private String abv;
    @NotNull
    @JsonIgnore
    private Double lat;
    @NotNull
    @JsonIgnore
    private Double lng;
    @NotNull
    private String city;

    @OneToMany(mappedBy = "from")
    @JsonIgnore
    private List<Schedule> departures;

    @OneToMany(mappedBy = "to")
    @JsonIgnore
    private List<Schedule> arrivals;


}
