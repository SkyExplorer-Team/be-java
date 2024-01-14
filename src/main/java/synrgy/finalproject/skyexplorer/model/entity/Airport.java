package synrgy.finalproject.skyexplorer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double lat;
    @NotNull
    private Double lng;

}
