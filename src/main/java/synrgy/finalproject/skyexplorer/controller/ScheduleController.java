package synrgy.finalproject.skyexplorer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy.finalproject.skyexplorer.model.dto.request.ScheduleDto;
import synrgy.finalproject.skyexplorer.service.ScheduleService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static synrgy.finalproject.skyexplorer.utils.ResponseGeneral.generatedResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<?> getSchedules(
            @RequestParam UUID from,
            @RequestParam UUID to,
            @RequestParam String departure

            ){
        LocalDateTime timeDeparture = LocalDateTime.parse(departure);
        var schedules = scheduleService.getSchedules(new ScheduleDto(from, to, timeDeparture));
        return generatedResponse("schedules", schedules);
    }

//    @PostMapping
//    public ResponseEntity<?> createSchedule(@RequestBody ScheduleDto scheduleDto){
//        var schedule = scheduleService.create(scheduleDto);
//        return generatedResponse("schedule", schedule);
//    }
}
