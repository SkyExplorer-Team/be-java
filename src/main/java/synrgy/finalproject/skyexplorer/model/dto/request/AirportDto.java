package synrgy.finalproject.skyexplorer.model.dto.request;

import synrgy.finalproject.skyexplorer.model.entity.National;

import java.util.UUID;

public record AirportDto(
        UUID nationalId,
        String name,
        String abv,
        Double lat,
        Double lng
) {
}
