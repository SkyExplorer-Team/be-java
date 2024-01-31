package synrgy.finalproject.skyexplorer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.model.dto.request.ScheduleDto;
import synrgy.finalproject.skyexplorer.model.entity.Schedule;
import synrgy.finalproject.skyexplorer.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    final private ScheduleRepository scheduleRepository;


    public List<Schedule> getSchedules(ScheduleDto scheduleDto){
        Pageable pageable = PageRequest.of(0, 6); // Limit to 6 items
        return scheduleRepository.findSchedules(
                scheduleDto.from(),
                scheduleDto.to(),
                scheduleDto.timeDeparture(),
                pageable);
    }
}
