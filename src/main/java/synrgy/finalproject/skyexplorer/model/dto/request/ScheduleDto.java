package synrgy.finalproject.skyexplorer.model.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleDto(
        UUID from,
        UUID to,
        LocalDateTime timeDeparture
){

}
