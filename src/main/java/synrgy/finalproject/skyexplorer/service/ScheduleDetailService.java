package synrgy.finalproject.skyexplorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.model.entity.Schedule;
import synrgy.finalproject.skyexplorer.repository.ScheduleDetailRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleDetailService {
    @Autowired
    private ScheduleDetailRepository scheduleDetailRepository;

    public List<Schedule> getSchedulesByCabinClassAndTicketTypeAndDate(
            UUID cabinClassId, UUID ticketTypeId, LocalDate date, UUID fromAirportId, UUID toAirportId) {
        return scheduleDetailRepository.findSchedulesByCabinClassAndTicketTypeAndDate(
                cabinClassId, ticketTypeId, date, fromAirportId, toAirportId);
    }
}
