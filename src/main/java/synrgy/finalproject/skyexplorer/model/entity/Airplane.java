package synrgy.finalproject.skyexplorer.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Airplane {
    @Id@GeneratedValue
    private UUID id;
    private String code;
    private String name;
    @JsonIgnore
    private Double speed;

}
