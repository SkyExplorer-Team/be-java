package synrgy.finalproject.skyexplorer.model.dto;

import lombok.*;
import lombok.experimental.Accessors;
import synrgy.finalproject.skyexplorer.model.entity.CabinClass;
import synrgy.finalproject.skyexplorer.model.entity.Schedule;
import synrgy.finalproject.skyexplorer.model.entity.TicketType;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ScheduleDetailDTO {
    private Schedule schedule;
    private TicketType ticketType;
    private CabinClass cabinClass;

    public ScheduleDetailDTO(Schedule schedule, TicketType ticketType, CabinClass cabinClass) {
        this.schedule = schedule;
        this.ticketType = ticketType;
        this.cabinClass = cabinClass;
    }
}
